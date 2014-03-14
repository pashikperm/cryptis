package usr.pashik.securd.platform.auth;

import usr.pashik.securd.platform.auth.exception.AlreadyAuthedException;
import usr.pashik.securd.platform.auth.exception.IncorrectCredentialsException;
import usr.pashik.securd.platform.auth.exception.IncorrectUserNameException;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.userbase.UserInfo;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pashik on 10.03.14 17:37.
 */
@ApplicationScoped
public class AuthUserService {
    @Inject
    UserbaseService userbaseService;

    Map<String, AuthedUser> authedUsers = new HashMap<>();
    Map<ConnectedClient, AuthedUser> auth2connUsers = new HashMap<>();

    public AuthedUser verifyCredentials(ConnectedClient user, String authString) throws IncorrectUserNameException, AlreadyAuthedException, IncorrectCredentialsException {
        int userIdPosition = authString.indexOf(":");
        if (userIdPosition == -1) {
            throw new IncorrectUserNameException();
        }

        String userId = authString.substring(0, userIdPosition);
        String password = authString.substring(userIdPosition + 1);

        if (userId.length() == 0 || password.length() == 0) {
            throw new IncorrectCredentialsException();
        }

        if (authedUsers.containsKey(userId)) {
            throw new AlreadyAuthedException();
        }
        UserInfo userInfo = userbaseService.getUserInfo(userId);
        if (userInfo == null) {
            throw new IncorrectCredentialsException();
        }
        if (!password.equals(userInfo.password)) {
            throw new IncorrectCredentialsException();
        }

        AuthedUser authedUser = new AuthedUser(user, userInfo);
        registerAuthedUser(authedUser);
        return authedUser;
    }

    public void registerAuthedUser(AuthedUser user) {
        authedUsers.put(user.info.id, user);
        auth2connUsers.put(user.client, user);
    }

    public void unregisterUser(ConnectedClient client) {
        AuthedUser user = auth2connUsers.get(client);
        auth2connUsers.remove(user);
        authedUsers.remove(user);
    }
}
