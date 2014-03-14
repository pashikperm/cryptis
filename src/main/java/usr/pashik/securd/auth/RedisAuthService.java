package usr.pashik.securd.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.auth.AuthedUserService;
import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.auth.exception.BruteforceAuthException;
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

    final String CREDENTIALS_ERROR = "ERR invalid password";
    final String ACCESS_EXCEPTION = "ERR operation not permitted";
    final String SUCCESS_AUTH = "OK";

    public AuthedUser authUser(ConnectedClient connectedClient) throws BruteforceAuthException, IOException, RedisProtocolWriteException, RedisProtocolReadException {
        reInject();

        RedisChannel client = new RedisChannel(connectedClient.getSocket());
        AuthedUser authedUser;
        int retryCount = 0;
        while (true) {
            RedisCommand command = client.readCommand();
            if (command.getMnemonic() == RedisCommandMnemonic.AUTH) {
                retryCount++;
                if (retryCount > config.getAuthRetryMaxCount()) {
                    throw new BruteforceAuthException(connectedClient);
                }
                try {
                    authedUser = authService.verifyCredentials(connectedClient, command.getPrimaryKey());
                    if (authedUser != null) {
                        RedisObject successAuthResponse = RedisObjectFabric.getSimpleString(SUCCESS_AUTH);
                        client.sendResponse(successAuthResponse);
                        break;
                    }
                } catch (Exception e) {
                    RedisObject authErrorResponse = RedisObjectFabric.getError(CREDENTIALS_ERROR);
                    client.sendResponse(authErrorResponse);
                    log.error("Auth exception", e);
                }
            } else {
                RedisObject notPermittedResponse = RedisObjectFabric.getError(ACCESS_EXCEPTION);
                client.sendResponse(notPermittedResponse);
            }
        }
        return authedUser;
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
                        RedisObject successAuthResponse = RedisObjectFabric.getSimpleString(SUCCESS_AUTH);
                        client.sendResponse(successAuthResponse);
                        break;
                    }
                } catch (Exception e) {
                    RedisObject authErrorResponse = RedisObjectFabric.getError(CREDENTIALS_ERROR);
                    client.sendResponse(authErrorResponse);
                    log.error("Auth exception", e);
                }
            } else {
                RedisObject notPermittedResponse = RedisObjectFabric.getError(ACCESS_EXCEPTION);
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
