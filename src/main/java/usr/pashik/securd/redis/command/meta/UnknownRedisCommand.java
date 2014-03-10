package usr.pashik.securd.redis.command.meta;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 10.03.14 22:26.
 */
public class UnknownRedisCommand extends RedisCommand {

    public UnknownRedisCommand(RedisObject rawCommand) {
        this.raw = rawCommand;
    }

    @Override
    public RedisCommandType getType() {
        return RedisCommandType.UNKNOWN;
    }

    @Override
    public RedisCommandFamily getFamily() {
        return RedisCommandFamily.UNKNOWN;
    }
}
