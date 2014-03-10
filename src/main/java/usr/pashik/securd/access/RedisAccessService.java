//package usr.pashik.securd.access;
//
//import usr.pashik.securd.access.rule.AccessRulesService;
//import usr.pashik.securd.platform.auth.AuthedUser;
//import usr.pashik.securd.platform.configurator.ConfiguratorService;
//import usr.pashik.securd.redis.command.RedisCommand;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//
///**
// * Created by pashik on 10.03.14 16:46.
// */
//@ApplicationScoped
//public class RedisAccessService {
//    @Inject
//    ConfiguratorService config;
//    @Inject
//    AccessRulesService accessRules;
//
//    private boolean verifyAccess(AuthedUser user, RedisCommand command) {
//        if (!config.isSecureMode()) {
//            return true;
//        }
//        AccessRules accessRules = accessService.getUserRules(user);
//        return accessRules.isAccessed(commandengine);
//    }
//}
