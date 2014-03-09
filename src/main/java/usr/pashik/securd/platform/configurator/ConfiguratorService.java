package usr.pashik.securd.platform.configurator;

import usr.pashik.securd.protocol.RESPUtil;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by pashik on 09.03.14 19:47.
 */
@ApplicationScoped
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
