//package usr.pashik.securd.auth;
//
//import usr.pashik.securd.platform.auth.AuthUserService;
//import usr.pashik.securd.platform.auth.exception.BruteforceAuthentificationException;
//import usr.pashik.securd.platform.configurator.ConfiguratorService;
//import usr.pashik.securd.platform.connection.ConnectedClient;
//import usr.pashik.securd.redis.command.RedisCommand;
//import usr.pashik.securd.redis.connection.RedisChannel;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import java.io.IOException;
//
///**
// * Created by pashik on 10.03.14 16:44.
// */
//@ApplicationScoped
//public class RedisAuthService {
//    @Inject
//    ConfiguratorService config;
//    @Inject
//    AuthUserService authService;
//
//    public void authUser(ConnectedClient user) throws BruteforceAuthentificationException, IOException {
//        if (!config.isSecureMode()) {
//            return;
//        }
//        int retryCount = 0;
//
//
//        RedisChannel client = new RedisChannel(user);
//        while (true) {
//            RedisCommand command = client.readCommand();
//            if (command.isAuthCommand()) {
//                retryCount++;
//                if (authService.verifyCredentials(user, command.authString)) {
//                    break;
//                }
//                if (retryCount > config.getAuthRetryMaxCount()) {
//                    throw new BruteforceAuthentificationException(user);
//                }
//            } else {
//                client.sendResponse(new RedisAuthError());
//            }
//        }
//    }
//}
