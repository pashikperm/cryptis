package usr.pashik.securd.platform.userbase.provider;

import usr.pashik.securd.platform.userbase.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by pashik on 10.03.14 17:53.
 */
public class DefaultUserbaseProvider extends UserbaseProvider {
    final Map<String, UserInfo> users = new HashMap<String, UserInfo>() {{
        put("pashik", new UserInfo("pashik", "pashik"));
        put("pashik1", new UserInfo("pashik1", "pashik1"));
    }};

    @Override
    public Map<String, UserInfo> fetchAllUsers() {
        return users;
    }

    @Override
    public boolean haveUser(UserInfo user) {
        return users.containsKey(user.id);
    }

    @Override
    public void updateUser(UserInfo user) {
        users.put(user.id, user);
    }

    @Override
    public int priority() {
        return 0;
    }
}
