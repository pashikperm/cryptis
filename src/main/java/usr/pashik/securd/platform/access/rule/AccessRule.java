package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 15.03.14 15:29.
 */
public class AccessRule {
    public AccessMode mode;
    public Operation operation;
    public Resource resource;

    public boolean isSatisfying(ProtocolCommand command) {
        if (operation.isSatisfying(command)) {
            if (operation.needToCheckResource()) {
                return resource.isSatisfying(command);
            } else return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("AccessRule [mode=%s, operation=%s, resource=%s", mode, operation, resource);
    }
}
