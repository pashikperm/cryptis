package usr.pashik.securd.proxy;

import usr.pashik.securd.platform.access.AccessService;
import usr.pashik.securd.platform.commandengine.ServerConsoleCommandWorker;
import usr.pashik.securd.platform.thread.BeanInjector;
import usr.pashik.securd.platform.userbase.UserbaseService;

/**
 * Created by pashik on 09.03.14 19:46.
 */
public class SecurdServiceActivator {

    public static void activateServices() {
        new Thread(new ServerConsoleCommandWorker()).start();
        BeanInjector.instance(UserbaseService.class).initialize();
        BeanInjector.instance(AccessService.class).initialize();
    }
}
