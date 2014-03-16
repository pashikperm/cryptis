package usr.pashik.securd.platform.userbase.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.inject.Inject;

/**
 * Created by pashik on 15.03.14 0:12.
 */
public class UpdateUserbaseListServerCommand extends ServerCommand {
    @Inject
    UserbaseService userbaseService;

    @Override
    public String getName() {
        return "updateUsersList";
    }

    @Override
    public String getDescription() {
        return "Updates all users from sources";
    }

    @Override
    public String execute(String[] args) throws Exception {
        userbaseService.reFetchAllUsers();
        return "User list updated";
    }
}
