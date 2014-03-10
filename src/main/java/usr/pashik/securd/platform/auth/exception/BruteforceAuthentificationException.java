package usr.pashik.securd.platform.auth.exception;

import usr.pashik.securd.platform.connection.ConnectedClient;

/**
 * Created by pashik on 10.03.14 17:33.
 */
public class BruteforceAuthentificationException extends Exception {
    public BruteforceAuthentificationException(ConnectedClient user) {
    }
}
