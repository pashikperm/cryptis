package usr.pashik.securd.proxy;

import usr.pashik.securd.platform.bean.BeanedRunner;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.meta.UnknownRedisCommand;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

import java.io.IOException;

/**
 * Created by pashik on 05.03.14 22:52.
 */
public class TestRunner extends BeanedRunner {
    public static void main(String[] args) throws IOException, RedisProtocolWriteException, RedisProtocolReadException {
        new SecurdRunner().initialize(args);
        SecurdServiceActivator.activateServices();

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

        RedisChannel server = new RedisChannel();
        server.sendCommand(command);

        RedisObject response = server.readResponse();
        System.out.println(response);
    }
}
