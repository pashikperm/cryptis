package usr.pashik.securd.platform.thread;

import usr.pashik.securd.platform.bean.BeanedRunner;

/**
 * Created by pashik on 10.03.14 13:31.
 */
public abstract class InjectedRunnable implements Runnable {
    @Override
    public void run() {
        BeanInjector.injectFields(BeanedRunner.getBeanManager(), this);
        runInjected();
    }

    public abstract void runInjected();
}
