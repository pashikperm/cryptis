package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 15.03.14 15:29.
 */
public class AccessBundle {
    public AccessRule[][] accessRules;

    public AccessMode getCommandAccessMode(AccessMode baseAccessMode, ProtocolCommand command) {
        AccessRule[] rules = accessRules[command.getId()];
        if (rules == null) return baseAccessMode;

        AccessMode result = baseAccessMode;
        for (AccessRule rule : rules) {
            if (rule.notEnoughArguments(command)) return AccessMode.DENY;
            if (rule.isSatisfying(command)) result = rule.mode;
        }
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("AccessBundle [");
        for (int i = 0; i < accessRules.length; i++) {
            AccessRule[] rules = accessRules[i];
            if (rules == null) continue;
            for (AccessRule rule : rules) {
                result.append(String.format("\n\t%commandId=%d rule=%s", i, rule));
            }
        }
        result.append("\n]");
        return result.toString();
    }
}
