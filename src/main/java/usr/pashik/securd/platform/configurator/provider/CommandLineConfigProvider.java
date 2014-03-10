package usr.pashik.securd.platform.configurator.provider;

import usr.pashik.securd.platform.bean.ArgumentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by pashik on 10.03.14 17:06.
 */
@ApplicationScoped
public class CommandLineConfigProvider implements ConfigProvider {
    @Inject
    ArgumentService argumentService;

    @Override
    public String getParameter(String key) {
        return argumentService.getArgument(key);
    }
}
