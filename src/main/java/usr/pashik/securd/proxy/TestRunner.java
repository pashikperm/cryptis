package usr.pashik.securd.proxy;

import usr.pashik.securd.platform.bean.BeanedRunner;
import usr.pashik.securd.platform.thread.BeanInjector;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.meta.command.UnknownRedisCommand;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.connection.RedisChannelService;
import usr.pashik.securd.redis.exception.RedisAuthException;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by pashik on 05.03.14 22:52.
 */
public class TestRunner extends BeanedRunner {
    @Inject
    RedisChannelService channelService;

    public static void main(String[] args) {
        try {
            new TestRunner().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(String[] args) throws RedisAuthException, RedisProtocolReadException, RedisProtocolWriteException, IOException {
        new SecurdRunner().initialize(args);
        SecurdServiceActivator.activateServices();

        BeanInjector.injectFields(this);

        RedisChannel server = channelService.getAuthedServerChannel();


        RedisObject name = new RedisObject();
        name.type = RedisObjectType.BULK;
        name.svalue = "keys";
        name.bvalue = name.svalue.getBytes();

        RedisObject arg = new RedisObject();

        RedisObject array = new RedisObject();
        array.type = RedisObjectType.ARRAY;
        array.avalue = new RedisObject[1];
        array.avalue[0] = name;

        RedisCommand command = new UnknownRedisCommand(array);

        server.sendCommand(command);

        RedisObject response = server.readResponse();
        System.out.println(response);
    }
}
