package usr.pashik.securd.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.auth.AuthedUserService;
import usr.pashik.securd.platform.auth.exception.BruteforceAuthException;
import usr.pashik.securd.platform.auth.exception.IncorrectTwoWayAuth;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.thread.BeanInjector;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.object.RedisObject;
import usr.pashik.securd.redis.protocol.object.RedisObjectFabric;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by pashik on 10.03.14 16:44.
 */
@ApplicationScoped
public class RedisAuthService {
    @Inject
    ConfiguratorService config;
    @Inject
    AuthedUserService authService;

    Logger log = LogManager.getLogger(RedisAuthService.class);

    public AuthedUser authUser(ConnectedClient connectedClient) throws BruteforceAuthException, IOException, RedisProtocolWriteException, RedisProtocolReadException {
        RedisChannel client = new RedisChannel(connectedClient.getSocket());
        RedisCommand command = client.readCommand();
        return reAuthUser(command, connectedClient);
    }

    public AuthedUser reAuthUser(RedisCommand authCommand, ConnectedClient connectedClient) throws BruteforceAuthException, IOException, RedisProtocolWriteException, RedisProtocolReadException {
        reInject();
        authService.unregisterUser(connectedClient);

        RedisChannel client = new RedisChannel(connectedClient.getSocket());
        AuthedUser authedUser;
        int retryCount = 0;
        boolean alreadyReceivedCommand = true;
        while (true) {
            RedisCommand command;
            if (alreadyReceivedCommand) {
                command = authCommand;
                alreadyReceivedCommand = false;
            } else {
                command = client.readCommand();
            }
            if (command.getMnemonic() == RedisCommandMnemonic.AUTH) {
                retryCount++;
                if (retryCount > config.getAuthRetryMaxCount()) {
                    throw new BruteforceAuthException(connectedClient);
                }
                try {
                    authedUser = authService.verifyCredentials(connectedClient, command.getPrimaryKey());
                    if (authedUser != null) {
                        if (!authedUser.getInfo().needTwoWayAuth()) {
                            authService.registerAuthedUser(authedUser);
                            RedisObject successAuthResponse = RedisObjectFabric.getSimpleString(RedisObjectFabric.SUCCESS_AUTH);
                            client.sendResponse(successAuthResponse);
                            break;
                        } else {
                            long seed = new SecureRandom().nextLong();
                            RedisObject twoWayResponse = RedisObjectFabric.getError(
                                    RedisObjectFabric.TWOWAY_AUTH_ERROR +
                                            seed);
                            client.sendResponse(twoWayResponse);

                            command = client.readCommand();
                            if (command.getMnemonic() != RedisCommandMnemonic.AUTH) {
                                throw new IncorrectTwoWayAuth();
                            }
                            authService.verifyTwoWayAuth(authedUser, seed, command.getPrimaryKey());
                            authService.registerAuthedUser(authedUser);
                            RedisObject successAuthResponse = RedisObjectFabric.getSimpleString(RedisObjectFabric.SUCCESS_AUTH);
                            client.sendResponse(successAuthResponse);
                            break;
                        }
                    }
                } catch (Exception e) {
                    RedisObject authErrorResponse = RedisObjectFabric.getError(RedisObjectFabric.CREDENTIALS_ERROR);
                    client.sendResponse(authErrorResponse);
                    log.error("Auth exception", e);
                }
            } else {
                RedisObject notPermittedResponse = RedisObjectFabric.getError(RedisObjectFabric.ACCESS_ERROR);
                client.sendResponse(notPermittedResponse);
            }
        }
        return authedUser;
    }

    private void reInject() {
        if (config == null || authService == null) {
            BeanInjector.injectFields(this);
        }
    }
}
