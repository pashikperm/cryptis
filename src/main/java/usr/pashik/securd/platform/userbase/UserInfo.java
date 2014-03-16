package usr.pashik.securd.platform.userbase;

/**
 * Created by pashik on 10.03.14 17:49.
 */
public class UserInfo {
    public String id;       // should be unique
    public String password;

    public UserInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%-15s [id=%s, password=%s]", "UserInfo", id, password);
    }
}
