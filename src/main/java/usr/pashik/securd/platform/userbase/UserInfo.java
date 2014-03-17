package usr.pashik.securd.platform.userbase;

/**
 * Created by pashik on 10.03.14 17:49.
 */
public class UserInfo {
    public String id;       // should be unique
    public String password;
    public String secretKey;

    public UserInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public UserInfo(String id, String password, String secretKey) {
        this.id = id;
        this.password = password;
        this.secretKey = secretKey;
    }

    public boolean needTwoWayAuth() {
        return secretKey != null;
    }

    @Override
    public String toString() {
        if (secretKey == null) {
            return String.format("%-15s [id=%s, password=%s]", "UserInfo", id, password);
        }
        return String.format("%-15s [id=%s, password=%s, secretKey=%s...]",
                             "UserInfo",
                             id,
                             password,
                             secretKey.substring(0, 10));
    }
}
