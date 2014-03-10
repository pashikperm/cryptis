package usr.pashik.securd.platform.configurator;

import usr.pashik.securd.platform.configurator.provider.DefaultConfigProvider;
import usr.pashik.securd.platform.configurator.provider.FileConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by pashik on 09.03.14 19:47.
 */
@ApplicationScoped
public class ConfiguratorService {
    @Inject
    DefaultConfigProvider defaultConfigProvider;
    @Inject
    FileConfigProvider fileConfigProvider;

    public int getProxyPort() {
        return Integer.parseInt(get("proxyPort"));
    }

    public int getServerPort() {
        return Integer.parseInt(get("redisPort"));
    }

    public String getServerHost() {
        return get("redisHost");
    }

    public String getParameter(String name) {
        return get(name);
    }

    public boolean isSecureMode() {
        return get("secureMode").equals("true");
    }

    public int getAuthRetryMaxCount() {
        return Integer.parseInt(get("authRetryMaxCount"));
    }

    private String get(String name) {
        String result = fileConfigProvider.getParameter(name);
        if (result != null) return result;
        result = defaultConfigProvider.getParameter(name);
        if (result != null) return result;
        return null;
    }
}
