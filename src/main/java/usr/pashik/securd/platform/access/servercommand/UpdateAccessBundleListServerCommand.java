package usr.pashik.securd.platform.access.servercommand;

import usr.pashik.securd.platform.access.AccessService;
import usr.pashik.securd.platform.commandengine.ServerCommand;

import javax.inject.Inject;

/**
 * Created by pashik on 16.03.14 19:18.
 */
public class UpdateAccessBundleListServerCommand extends ServerCommand {
    @Inject
    AccessService accessService;

    @Override
    public String getName() {
        return "updateAccessList";
    }

    @Override
    public String getDescription() {
        return "Updates all access lists of server";
    }

    @Override
    public String execute(String[] args) throws Exception {
        accessService.reFetchAllBundles();
        return "Access list updated";
    }
}
