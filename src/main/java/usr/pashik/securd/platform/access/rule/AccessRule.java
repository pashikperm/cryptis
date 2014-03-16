package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 15.03.14 15:29.
 */
public class AccessRule {
    public AccessMode mode;
    public AccessResource resource;

    public AccessRule(AccessMode mode, AccessResource resource) {
        this.mode = mode;
        this.resource = resource;
    }

    public boolean isSatisfying(ProtocolCommand command) {
        if (resource == null) return true;
        return resource.isSatisfying(command);
    }

    public boolean notEnoughArguments(ProtocolCommand command) {
        if (resource == null) return false;
        return resource.notEnoughArguments(command);
    }

    @Override
    public String toString() {
        if (resource == null) return String.format("AccessRule [mode=%s, resource=*]", mode);
        return String.format("AccessRule [mode=%s, resource=%s]", mode, resource);
    }
}
