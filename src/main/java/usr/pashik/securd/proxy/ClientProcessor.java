//package usr.pashik.securd.proxy;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import usr.pashik.securd.platform.configurator.ConfiguratorService;
//import usr.pashik.securd.platform.users.ConnectedUser;
//import usr.pashik.securd.platform.users.ConnectedUserAnnotation;
//import usr.pashik.securd.redis.RedisClient;
//import usr.pashik.securd.redis.RedisServer;
//
//import javax.inject.Inject;
//import java.io.IOException;
//
///**
// * Created by pashik on 06.03.14 23:47.
// */
//public class ClientProcessor implements Runnable {
//    @Inject
//    @ConnectedUserAnnotation
//    ConnectedUser user;
//
//    @Inject
//    AccessService accessService;
//
//    @Inject
//    ConfiguratorService config;
//
//    Logger log = LogManager.getLogger(ClientProcessor.class);
//
//    RedisClient client;
//    RedisServer server;
//
//    public ClientProcessor(RedisClient client, RedisServer server) throws IOException {
//        this.client = client;
//        this.server = server;
//    }
//
//    @Override
//    public void run() {
//        try {
//            authUser();
//            while (true) {
//                RedisCommand command = client.readCommand();
//
//                if (!verifyAccess(command)) {
//                    client.sendResponse(new RedisAccessError());
//                    continue;
//                }
//
//                server.sendCommand(command);
//                RedisResponse response = server.readResponse();
//                client.sendResponse(response);
//            }
//        } catch (IOException e) {
//            log.error(e);
//        } finally {
//            userService.unregisterConnection(client);
//        }
//    }
//
//    private boolean verifyAccess(RedisCommand command) {
//        if (config.isSecureMode()) {
//            AccessRules accessRules = accessService.getUserRules(user);
//            return accessRules.isAccessed(command);
//        }
//        return true;
//    }
//
//    private void authUser() {
//        if (config.isSecureMode()) {
//            int retryCount = 0;
//            while (true) {
//                RedisCommand command = client.readCommand();
//
//                if (command.isAuthCommand()) {
//                    retryCount++;
//                    if (accessService.verifyCredentials(user, command)) {
//                        break;
//                    }
//                    if (retryCount > config.getAuthRetryMaxCount()) {
//                        throw new BruteforceAuthentification(client);
//                    }
//                } else {
//                    client.sendResponse(new RedisAuthError());
//                }
//            }
//        }
//    }
//}
