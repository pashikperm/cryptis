package usr.pashik.securd.platform.configurator.provider;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.platform.configurator.ConfiguratorService;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by pashik on 10.03.14 17:05.
 */
public abstract class ConfigProvider implements Comparable<ConfigProvider> {
    public abstract String getParameter(String key);

    public abstract int priority();

    @Inject
    ConfiguratorService config;

    public void onContainerInitialized(@Observes ContainerInitialized initialized) {
        config.registerProvider(this);
    }

    @Override
    public int compareTo(ConfigProvider provider) {
        if (priority() > provider.priority()) {
            return 1;
        } else if (priority() < provider.priority()) {
            return -1;
        }
        return 0;
    }
}
