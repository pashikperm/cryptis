package usr.pashik.securd.platform.access.servercommand;

import usr.pashik.securd.platform.access.AccessService;
import usr.pashik.securd.platform.access.rule.AccessBundle;
import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.userbase.UserInfo;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by pashik on 10.03.14 17:42.
 */
public class AccessBundleListServerCommand extends ServerCommand {
    @Inject
    AccessService accessService;

    @Override
    public String getName() {
        return "access";
    }

    @Override
    public String getDescription() {
        return "Prints all access rules";
    }

    @Override
    public String execute(String[] args) {
        Map<UserInfo, AccessBundle> bundleSet = accessService.getAccessBundles();
        StringBuilder result = new StringBuilder("Access base policy: " + accessService.getBaseAccessMode());
        result.append("\nAccess bundle list:\n");
        for (Map.Entry<UserInfo, AccessBundle> bundle : bundleSet.entrySet()) {
            result.append(String.format("%s bundle %s", bundle.getKey(), bundle.getValue()));
        }
        return result.toString();
    }
}
