package usr.pashik.securd.platform.auth;

import usr.pashik.securd.platform.auth.exception.IncorrectCredentialsException;
import usr.pashik.securd.platform.auth.exception.IncorrectUserNameException;
import usr.pashik.securd.platform.userbase.UserInfo;

/**
 * Created by pashik on 15.03.14 15:09.
 */
public class AuthCredentialsSerializer {

    public static String getUserId(String authLine) throws IncorrectUserNameException {
        int userIdPosition = authLine.indexOf(":");
        if (userIdPosition == -1) {
            throw new IncorrectUserNameException();
        }

        String userId = authLine.substring(0, userIdPosition);
        if (userId.length() == 0) {
            throw new IncorrectUserNameException();
        }
        return userId;
    }

    public static String getPassword(String authLine) throws IncorrectCredentialsException {
        int userIdPosition = authLine.indexOf(":");
        if (userIdPosition == -1) {
            throw new IncorrectCredentialsException();
        }

        String password = authLine.substring(userIdPosition + 1);
        if (password.length() == 0) {
            throw new IncorrectCredentialsException();
        }
        return password;
    }

    public static String getAuthLine(UserInfo user) {
        return user.id + ":" + user.password;
    }
}