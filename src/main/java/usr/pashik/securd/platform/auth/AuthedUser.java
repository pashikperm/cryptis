package usr.pashik.securd.platform.auth;

import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.userbase.UserInfo;

/**
 * Created by pashik on 10.03.14 17:37.
 */
public class AuthedUser {
    String id;
    UserInfo info;
    ConnectedClient client;

    AuthedUser(ConnectedClient client, UserInfo userInfo) {
        id = userInfo.id;
        info = userInfo;
        this.client = client;
    }
}
