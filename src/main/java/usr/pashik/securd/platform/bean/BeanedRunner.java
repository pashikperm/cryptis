package usr.pashik.securd.platform.bean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.spi.BeanManager;

/**
 * Created by pashik on 10.03.14 14:20.
 */
public abstract class BeanedRunner {
    static Weld weld;
    static WeldContainer weldContainer;

    public void initialize(String[] args) {
        weld = new Weld();
        weldContainer = weld.initialize();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(weld));

        weldContainer.instance().select(ArgumentService.class).get().setStartArguments(args);
    }

    public static synchronized BeanManager getBeanManager() {
        return weldContainer.getBeanManager();
    }

    public static synchronized WeldContainer getWeldContainer() {
        return weldContainer;
    }

    static class ShutdownHook extends Thread {
        private Weld weld;

        public ShutdownHook(Weld weld) {
            this.weld = weld;
        }

        public void run() {
            weld.shutdown();
        }
    }
}
