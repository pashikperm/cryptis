package usr.pashik.securd.platform.userbase.servercommand;

import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.userbase.UserInfo;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by pashik on 15.03.14 0:12.
 */
public class UserbaseListServerCommand extends ServerCommand {
    @Inject
    UserbaseService userbaseService;

    @Override
    public String getName() {
        return "userbase";
    }

    @Override
    public String getDescription() {
        return "Prints all registered users from userbase";
    }

    @Override
    public String execute(String[] args) throws Exception {
        Collection<UserInfo> users = userbaseService.getRegisteredUsers();
        StringBuilder result = new StringBuilder("Registered users list:\n");
        for (UserInfo user : users) {
            result.append(user);
            result.append("\n");
        }
        return result.toString();
    }
}
