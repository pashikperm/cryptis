package usr.pashik.securd.redis.command.info;

/**
 * Created by pashik on 10.03.14 22:23.
 */
public enum RedisCommandType {
    READ, WRITE, CREATE, DELETE, INFO,      // data
    SERVICE,                // without data access
    UNKNOWN                 // unknown
}
