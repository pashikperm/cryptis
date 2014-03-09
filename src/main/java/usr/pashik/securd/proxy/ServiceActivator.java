package usr.pashik.securd.proxy;

import usr.pashik.securd.platform.commands.ServerConsoleCommandWorker;

/**
 * Created by pashik on 09.03.14 19:46.
 */
public class ServiceActivator {
    public static void activateServices() {
        new Thread(new ServerConsoleCommandWorker()).start();
    }
}
