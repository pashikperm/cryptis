package usr.pashik.securd.platform.configurator.provider;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

/**
 * Created by pashik on 10.03.14 17:08.
 */
@ApplicationScoped
public class DefaultConfigProvider implements ConfigProvider {
    final HashMap<String, String> defaults = new HashMap<String, String>() {{
        put("proxyPort", "7070");
        put("redisPort", "6379");
        put("redisHost", "localhost");
        put("secureMode", "false");
        put("authRetryMaxCount", "3");
    }};

    @Override
    public String getParameter(String key) {
        return defaults.get(key);
    }
}
