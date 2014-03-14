package usr.pashik.securd.platform.userbase.provider;

import usr.pashik.securd.platform.userbase.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pashik on 10.03.14 17:52.
 */
public class RedisUserbaseProvider extends UserbaseProvider {
    @Override
    public Map<String, UserInfo> fetchAllUsers() {
        return new HashMap<>();
    }

    @Override
    public boolean haveUser(UserInfo user) {
        return false;
    }

    @Override
    public void updateUser(UserInfo user) {

    }

    @Override
    public int priority() {
        return 3;
    }
}
