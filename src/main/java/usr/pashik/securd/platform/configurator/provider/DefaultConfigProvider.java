package usr.pashik.securd.platform.configurator.provider;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

/**
 * Created by pashik on 10.03.14 17:08.
 */
@ApplicationScoped
public class DefaultConfigProvider extends ConfigProvider {
    final HashMap<String, String> defaults = new HashMap<String, String>() {{
        put("proxyPort", "7070");

        put("redisHost", "localhost");
        put("redisPort", "6379");
        put("redisPassword", "pashik");

        put("secureMode", "true");
        put("authRetryMaxCount", "3");
    }};

    @Override
    public String getParameter(String key) {
        return defaults.get(key);
    }

    @Override
    public int priority() {
        return 0;
    }
}
