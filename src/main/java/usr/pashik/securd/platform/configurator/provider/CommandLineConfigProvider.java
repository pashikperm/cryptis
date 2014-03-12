package usr.pashik.securd.platform.configurator.provider;

import usr.pashik.securd.platform.bean.ArgumentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by pashik on 10.03.14 17:06.
 */
@ApplicationScoped
public class CommandLineConfigProvider extends ConfigProvider {
    @Inject
    ArgumentService argumentService;

    @Override
    public String getParameter(String key) {
        return argumentService.getArgument(key);
    }

    @Override
    public int priority() {
        return 3;
    }
}
