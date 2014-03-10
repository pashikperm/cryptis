package usr.pashik.securd.redis.command.meta;

import usr.pashik.securd.redis.command.RedisCommand;

/**
 * Created by pashik on 11.03.14 1:34.
 */
public class NoKeyRedisCommand extends RedisCommand {
    @Override
    public RedisCommandType getType() {
        return null;
    }

    @Override
    public RedisCommandFamily getFamily() {
        return null;
    }
}
