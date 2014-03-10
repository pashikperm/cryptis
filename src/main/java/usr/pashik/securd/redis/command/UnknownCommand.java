package usr.pashik.securd.redis.command;

import usr.pashik.securd.redis.command.meta.RedisCommandFamily;
import usr.pashik.securd.redis.command.meta.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 10.03.14 22:26.
 */
public class UnknownCommand extends RedisCommand {

    public UnknownCommand(RedisObject innerRepresentation) {
        this.innerRepresentation = innerRepresentation;
    }

    @Override
    public RedisCommandType getType() {
        return null;
    }

    @Override
    public RedisCommandFamily getFamily() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public RedisObject getInnerRepresentation() {
        return null;
    }

    @Override
    public String getPrimaryKey() {
        return null;
    }

    @Override
    public String getSecondaryKey() {
        return null;
    }

    @Override
    public RedisObject getArguments() {
        return null;
    }
}
