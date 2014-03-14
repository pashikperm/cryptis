package usr.pashik.securd.platform.auth.servercommand;

import usr.pashik.securd.platform.auth.AuthedUser;
import usr.pashik.securd.platform.auth.AuthedUserService;
import usr.pashik.securd.platform.commandengine.ServerCommand;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by pashik on 10.03.14 17:45.
 */
public class AuthedUsersListServerCommand extends ServerCommand {
    @Inject
    AuthedUserService authedUserService;

    @Override
    public String getName() {
        return "authedUsers";
    }

    @Override
    public String getDescription() {
        return "Prints all active authed users";
    }

    @Override
    public String execute(String[] args) {
        Collection<AuthedUser> authedUsers = authedUserService.getUsers();
        StringBuilder result = new StringBuilder("Authed users list:\n");
        for (AuthedUser authedUser : authedUsers) {
            result.append(authedUser);
            result.append("\n");
        }
        return result.toString();
    }
}
