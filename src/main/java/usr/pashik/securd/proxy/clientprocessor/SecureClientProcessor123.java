//package usr.pashik.securd.proxy.clientprocessor;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import usr.pashik.securd.platform.configurator.ConfiguratorService;
//import usr.pashik.securd.platform.connection.ConnectedClient;
//import usr.pashik.securd.platform.thread.InjectedRunnable;
//import usr.pashik.securd.redis.connection.RedisChannel;
//
//import javax.inject.Inject;
//
///**
// * Created by pashik on 06.03.14 23:47.
// */
//public class SecureClientProcessor extends InjectedRunnable {
//    ConnectedClient user;
//
////    @Inject
////    RedisAuthService authService;
//
////    @Inject
////    RedisAccessService accessService;
//
//    @Inject
//    ConfiguratorService config;
//
//    Logger log = LogManager.getLogger(SecureClientProcessor.class);
//
//    RedisChannel client;
//    RedisChannel server;
//
//    SecureClientProcessor() {
//    }
//
//    @Override
//    public void runInjected() {
////
////        redis localhost:7070> get x
////                (nil)
////        redis localhost:7070> set x
////                (error) ERR wrong number of arguments for 'set' command
////        redis localhost:7070> get x
////                (nil)
////        redis localhost:7070> set x 123
////        OK
////        redis localhost:7070> keys *
////                1) "x"
////        redis localhost:7070> get x
////        "123"
////        redis localhost:7070> auth pashik1
////                (error) ERR invalid password
////        redis localhost:7070> get x
////                (error) ERR operation not permitted
////        redis localhost:7070>
////        try {
////            authService.authUser(user);
////            while (true) {
////                RedisCommand command = client.readCommand();
////
////                boolean hasAccess = accessService.verifyAccess(user, command);
////                if (!hasAccess) {
////                    client.sendResponse(new RedisAccessError());
////                    continue;
////                }
////
////                server.sendCommand(command);
////                RedisObject response = server.readResponse();
////                client.sendResponse(response);
////            }
////        } catch (IOException e) {
////            log.error(e);
////        } finally {
////            userService.unregisterConnection(client);
////        }
//    }
//
//}
