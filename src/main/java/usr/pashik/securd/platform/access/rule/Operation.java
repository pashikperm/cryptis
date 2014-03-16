package usr.pashik.securd.platform.access.rule;

import usr.pashik.securd.platform.protocol.ProtocolCommand;

/**
 * Created by pashik on 16.03.14 11:11.
 */
public interface Operation {
    public boolean isSatisfying(ProtocolCommand command);

    public boolean needToCheckResource();
}
