package usr.pashik.securd.platform.userbase;

/**
 * Created by pashik on 10.03.14 17:49.
 */
public class UnknownUserInfo extends UserInfo {
    public UnknownUserInfo(String id) {
        super(id, "");
    }

    @Override
    public String toString() {
        return String.format("%-15s [id=%s]", "UnknownUserInfo", id);
    }
}
