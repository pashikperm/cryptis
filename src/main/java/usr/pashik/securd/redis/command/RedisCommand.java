package usr.pashik.securd.redis.command;

import usr.pashik.securd.redis.command.meta.RedisCommandFamily;
import usr.pashik.securd.redis.command.meta.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 10.03.14 17:20.
 */
public abstract class RedisCommand {
    public abstract RedisCommandType getType();

    public abstract RedisCommandFamily getFamily();

    public abstract String getName();

    public abstract String getPrimaryKey();

    public abstract String getSecondaryKey();

    public abstract RedisObject getArguments();

    RedisObject innerRepresentation;

    public RedisObject getInnerRepresentation() {
        return innerRepresentation;
    }
}
