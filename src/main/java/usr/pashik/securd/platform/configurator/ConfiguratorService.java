package usr.pashik.securd.platform.configurator;

import usr.pashik.securd.platform.configurator.provider.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by pashik on 09.03.14 19:47.
 */
@ApplicationScoped
public class ConfiguratorService {
    Set<ConfigProvider> providers = new TreeSet<>();

    public int getProxyPort() {
        return Integer.parseInt(get("proxyPort"));
    }

    // SERVER
    public String getServerHost() {
        return get("redisHost");
    }

    public int getServerPort() {
        return Integer.parseInt(get("redisPort"));
    }

    public String getServerPassword() {
        return get("redisPassword");
    }


    public boolean isSecureMode() {
        return get("secureMode").equals("true");
    }

    public int getAuthRetryMaxCount() {
        return Integer.parseInt(get("authRetryMaxCount"));
    }

    public String getParameter(String name) {
        return get(name);
    }

    private String get(String name) {
        for (ConfigProvider provider : providers) {
            String result = provider.getParameter(name);
            if (result != null) return result;
        }
        return null;
    }

    public void registerProvider(ConfigProvider provider) {
        providers.add(provider);
    }
}
