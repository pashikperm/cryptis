package usr.pashik.securd.platform.configurator.provider;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by pashik on 10.03.14 17:07.
 */
@ApplicationScoped
public class RedisConfigProvider extends ConfigProvider {
    @Override
    public String getParameter(String key) {
        return null;
    }

    @Override
    public int priority() {
        return 2;
    }
}
