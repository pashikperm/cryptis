package usr.pashik.securd.proxy.clientprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.thread.InjectedRunnable;
import usr.pashik.securd.redis.connection.RedisChannel;

import javax.inject.Inject;

/**
 * Created by pashik on 06.03.14 23:47.
 */
public class SecureClientProcessor extends InjectedRunnable {
    ConnectedClient user;

//    @Inject
//    RedisAuthService authService;

//    @Inject
//    RedisAccessService accessService;

    @Inject
    ConfiguratorService config;

    Logger log = LogManager.getLogger(SecureClientProcessor.class);

    RedisChannel client;
    RedisChannel server;

    SecureClientProcessor() {
    }

    @Override
    public void runInjected() {
//        try {
//            authService.authUser(user);
//            while (true) {
//                RedisCommand command = client.readCommand();
//
//                boolean hasAccess = accessService.verifyAccess(user, command);
//                if (!hasAccess) {
//                    client.sendResponse(new RedisAccessError());
//                    continue;
//                }
//
//                server.sendCommand(command);
//                RedisObject response = server.readResponse();
//                client.sendResponse(response);
//            }
//        } catch (IOException e) {
//            log.error(e);
//        } finally {
//            userService.unregisterConnection(client);
//        }
    }

}
