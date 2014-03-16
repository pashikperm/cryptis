package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 15.03.14 15:29.
 */
public class AccessBundle {
    AccessRule[] accessRules;

    public AccessBundle(AccessRule[] accessRules) {
        this.accessRules = accessRules;
    }

    public boolean isAllowedCommand(AccessMode baseAccessMode, ProtocolCommand command) {
        AccessMode result = baseAccessMode;
        for (AccessRule rule : accessRules) {
            if (rule.isSatisfying(command)) {
                result = rule.mode;
            }
        }
        return result == AccessMode.ALLOW;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("AccessBundle [");
        for (AccessRule accessRule : accessRules) {
            result.append("\n\t");
            result.append(accessRule);
        }
        result.append("]");
        return result.toString();
    }
}
