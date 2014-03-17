package usr.pashik.securd.platform.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.crypto.BinaryService;
import usr.pashik.securd.crypto.CryptoService;
import usr.pashik.securd.platform.auth.exception.AlreadyAuthedException;
import usr.pashik.securd.platform.auth.exception.IncorrectCredentialsException;
import usr.pashik.securd.platform.auth.exception.IncorrectUserNameException;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.userbase.UserInfo;
import usr.pashik.securd.platform.userbase.UserbaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pashik on 10.03.14 17:37.
 */
@ApplicationScoped
public class AuthedUserService {
    @Inject
    UserbaseService userbaseService;

    Logger log = LogManager.getLogger(AuthedUserService.class);

    Map<String, AuthedUser> authedUsers = new HashMap<>();
    Map<ConnectedClient, AuthedUser> auth2connUsers = new HashMap<>();

    public boolean preVerifyCredentials(ConnectedClient connectedClient, String authString) throws IncorrectCredentialsException, AlreadyAuthedException, IncorrectUserNameException {
        return verifyCredentials(connectedClient, authString) != null;
    }

    public AuthedUser verifyCredentials(ConnectedClient connectedClient, String authString) throws IncorrectUserNameException, AlreadyAuthedException, IncorrectCredentialsException {
        String userId = AuthCredentialsSerializer.getUserId(authString);
        String password = AuthCredentialsSerializer.getPassword(authString);

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

        return new AuthedUser(connectedClient, userInfo);
    }

    public void verifyTwoWayAuth(AuthedUser authedUser, long seed, String authString) throws IncorrectCredentialsException {
        if (authString == null) {
            throw new IncorrectCredentialsException();
        }
        String secretKeyString = authedUser.getInfo().secretKey;
        byte[] secretKey = BinaryService.base642bytes(secretKeyString);
        if (!authString.equals(CryptoService.hmacSha1(Long.toString(seed), secretKey))) {
            throw new IncorrectCredentialsException();
        }
    }

    public void registerAuthedUser(AuthedUser user) {
        authedUsers.put(user.info.id, user);
        auth2connUsers.put(user.client, user);
        log.info(String.format("registerAuthedUser %s", user));
    }

    public void unregisterUser(ConnectedClient client) {
        AuthedUser user = auth2connUsers.get(client);
        if (user != null) {
            auth2connUsers.remove(client);
            authedUsers.remove(user.id);
            log.info(String.format("unregisterAuthedUser %s", user));
        }
    }

    public AuthedUser getAuthedUser(ConnectedClient connectedClient) {
        return auth2connUsers.get(connectedClient);
    }

    public Collection<AuthedUser> getUsers() {
        return authedUsers.values();
    }
}
