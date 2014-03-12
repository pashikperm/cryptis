package usr.pashik.securd.platform.configurator.provider;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by pashik on 10.03.14 17:06.
 */
@ApplicationScoped
public class FileConfigProvider extends ConfigProvider {
    @Override
    public String getParameter(String key) {
        return null;
    }

    @Override
    public int priority() {
        return 1;
    }
}
