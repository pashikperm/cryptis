package usr.pashik.securd.configurator;

import usr.pashik.securd.protocol.RESPUtil;


/**
 * Created by pashik on 09.03.14 19:47.
 */
public class ConfiguratorService {
    public int getProxyPort() {
        return 7070;
    }

    public int getServerPort() {
        return RESPUtil.DEFAULT_PORT;
    }

    public Object getServerHost() {
        return RESPUtil.DEFAULT_HOST;
    }
}
