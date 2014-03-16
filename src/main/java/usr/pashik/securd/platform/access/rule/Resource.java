package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 16.03.14 11:12.
 */
public interface Resource {
    public boolean isSatisfying(ProtocolCommand command);
}
