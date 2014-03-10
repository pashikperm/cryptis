package usr.pashik.securd.redis.protocol.command;

/**
 * Created by pashik on 09.03.14 19:11.
 */
public interface RedisParametrizedCommand {
    public Object protocolRepresentation(Object... args);
}
