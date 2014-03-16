package usr.pashik.securd.access.rule;

import usr.pashik.securd.platform.access.rule.AccessBundle;
import usr.pashik.securd.platform.access.rule.AccessRule;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;

/**
 * Created by pashik on 16.03.14 17:08.
 */
public class RedisAccessBundle extends AccessBundle {
    public RedisAccessBundle() {
        accessRules = new AccessRule[RedisCommandMnemonic.values().length][];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("AccessBundle [");
        for (int i = 0; i < accessRules.length; i++) {
            AccessRule[] rules = accessRules[i];
            if (rules == null) continue;
            for (AccessRule rule : rules) {
                result.append(String.format("\n\tcommand=%10s rule=%s",
                                            RedisCommandMnemonic.values()[i].name(),
                                            rule));
            }
        }
        result.append("\n]");
        return result.toString();
    }
}
