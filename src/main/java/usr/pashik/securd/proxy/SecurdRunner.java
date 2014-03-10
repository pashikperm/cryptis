package usr.pashik.securd.proxy;

import usr.pashik.securd.bean.BeanedRunner;

import java.io.IOException;

/**
 * Created by pashik on 06.03.14 23:43.
 */
public class SecurdRunner extends BeanedRunner {
    public static void main(String[] args) throws IOException {
        new SecurdRunner().initialize(args);
        getWeldContainer().instance().select(SecurdApplication.class).get().start();
    }
}

