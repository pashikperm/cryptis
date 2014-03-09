package usr.pashik.securd.redis.protocol.commands;

/**
 * Created by pashik on 09.03.14 19:04.
 */
public class ParametrizedRedisCommand implements RedisParametrizedCommand {
    final String name;

    public ParametrizedRedisCommand(String name) {
        this.name = name;
    }

    @Override
    public Object protocolRepresentation(Object... args) {
        Object[] result = new Object[args.length + 1];
        result[0] = name;
        for (int i = 0; i < args.length; i++) {
            result[i] = args;
        }
        return result;
    }
}
