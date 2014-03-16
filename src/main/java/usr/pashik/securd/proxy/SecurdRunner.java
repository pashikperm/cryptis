package usr.pashik.securd.proxy;

import usr.pashik.securd.platform.bean.BeanedRunner;
import usr.pashik.securd.platform.thread.BeanInjector;
import usr.pashik.securd.redis.exception.RedisAuthException;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;

import java.io.IOException;

/**
 * Created by pashik on 06.03.14 23:43.
 */
public class SecurdRunner extends BeanedRunner {
    public static void main(String[] args) throws IOException, RedisProtocolReadException, RedisAuthException, RedisProtocolWriteException {
        new SecurdRunner().initialize(args);

        SecurdServiceActivator.activateServices();
        BeanInjector.instance(SecurdApplication.class).start();
    }
}

