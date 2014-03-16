package usr.pashik.securd.proxy.clientprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.auth.RedisAuthService;
import usr.pashik.securd.platform.access.AccessService;
import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.connection.ConnectedClientService;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.protocol.object.RedisObject;
import usr.pashik.securd.redis.protocol.object.RedisObjectFabric;

import javax.inject.Inject;

/**
 * Created by pashik on 06.03.14 23:47.
 */
public class SecureClientProcessor extends ClientProcessor {
    @Inject
    ConfiguratorService config;
    @Inject
    ConnectedClientService clientService;
    @Inject
    RedisAuthService authService;
    @Inject
    AccessService accessService;

    RedisChannel client;
    RedisChannel server;

    Logger log = LogManager.getLogger(SecureClientProcessor.class);

    @Override
    public void runInjected() {
        ConnectedClient connectedClient = clientService.registerConnection(client.getSocket());
        try {
            AuthedUser authedUser = authService.authUser(connectedClient);
            while (true) {
                RedisCommand command = client.readCommand();
                if (command.getMnemonic() == RedisCommandMnemonic.AUTH) {
                    authedUser = authService.reAuthUser(command, connectedClient);
                    continue;
                }
                if (!accessService.verifyAccess(authedUser, command)) {
                    RedisObject response = RedisObjectFabric.getError(RedisObjectFabric.ACCESS_EXCEPTION);
                    client.sendResponse(response);
                    log.info(String.format("Non accessed command %s", command));
                    continue;
                }
                log.info(command);
                server.sendCommand(command);
                RedisObject response = server.readResponse();
                client.sendResponse(response);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            clientService.unregisterConnection(connectedClient);
        }
    }
}
