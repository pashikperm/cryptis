package usr.pashik.securd.platform.access;

import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.access.provider.AccessBundleProvider;
import usr.pashik.securd.platform.access.rule.AccessBundle;
import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.protocol.ProtocolCommand;
import usr.pashik.securd.platform.userbase.UnknownUserInfo;
import usr.pashik.securd.platform.userbase.UserInfo;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by pashik on 10.03.14 17:39.
 */
@ApplicationScoped
public class AccessService {
    @Inject
    UserbaseService userbaseService;

    AccessMode baseAccessMode = AccessMode.DENY;
    Map<String, AccessBundle> accessBundles = new HashMap<>();
    Set<AccessBundleProvider> providers = new HashSet<>();

    public AccessBundle getAccessBundle(String userId) {
        return accessBundles.get(userId);
    }

    public AccessBundle getAccessBundle(AuthedUser user) {
        return accessBundles.get(user.getInfo().id);
    }

    public void reFetchAllBundles() {
        accessBundles.clear();
        for (AccessBundleProvider provider : providers) {
            reFetchBundles(provider);
        }
    }

    public boolean verifyAccess(AuthedUser user, ProtocolCommand command) {
        AccessBundle accessBundle = getAccessBundle(user);
        if (accessBundle == null) {
            return baseAccessMode == AccessMode.ALLOW;
        }
        return accessBundle.getCommandAccessMode(baseAccessMode, command) == AccessMode.ALLOW;
    }

    public void reFetchBundles(AccessBundleProvider provider) {
        accessBundles.putAll(provider.reFetchAllBundles());
        baseAccessMode = provider.getBaseAccessMode();
    }

    public Map<UserInfo, AccessBundle> getAccessBundles() {
        Map<UserInfo, AccessBundle> result = new HashMap<>();
        for (Map.Entry<String, AccessBundle> bundleEntry : accessBundles.entrySet()) {
            UserInfo userInfo = userbaseService.getUserInfo(bundleEntry.getKey());
            if (userInfo == null) {
                userInfo = new UnknownUserInfo(bundleEntry.getKey());
            }
            result.put(userInfo, bundleEntry.getValue());
        }
        return result;
    }

    public void registerProvider(AccessBundleProvider bundleProvider) {
        providers.add(bundleProvider);
    }

    public AccessMode getBaseAccessMode() {
        return baseAccessMode;
    }

    public void initialize() {
        reFetchAllBundles();
    }
}
