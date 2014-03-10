package usr.pashik.securd.platform.userbase.provider;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.platform.userbase.UserInfo;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by pashik on 10.03.14 17:49.
 */
public abstract class UserbaseProvider implements Comparable<UserbaseProvider> {
    public abstract Map<String, UserInfo> fetchAllUsers();

    public abstract boolean haveUser(UserInfo user);

    public abstract void updateUser(UserInfo user);

    public abstract int priority();


    @Inject
    UserbaseService userbaseService;

    public void onContainerInitialized(@Observes ContainerInitialized initialized) {
        userbaseService.registerProvider(this);
    }

    @Override
    public int compareTo(UserbaseProvider provider) {
        if (priority() > provider.priority()) {
            return 1;
        } else if (priority() < provider.priority()) {
            return -1;
        }
        return 0;
    }
}
