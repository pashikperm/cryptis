package usr.pashik.securd.platform.access.provider;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.platform.access.AccessService;
import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.access.rule.AccessBundle;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by pashik on 15.03.14 16:28.
 */
public abstract class AccessBundleProvider {
    @Inject
    AccessService accessService;

    public abstract Map<String, AccessBundle> reFetchAllBundles();

    public void onContainerInitialized(@Observes ContainerInitialized initialized) {
        accessService.registerProvider(this);
    }

    public abstract AccessMode getBaseAccessMode();
}
