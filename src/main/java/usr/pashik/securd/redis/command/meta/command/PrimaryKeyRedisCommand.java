package usr.pashik.securd.redis.command.meta.command;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

/**
 * Created by pashik on 11.03.14 1:34.
 */
public class PrimaryKeyRedisCommand extends RedisCommand {
    public PrimaryKeyRedisCommand(RedisCommandMnemonic mnemonic, RedisCommandType type, RedisCommandFamily family, RedisObject raw) {
        this.mnemonic = mnemonic;
        this.type = type;
        this.family = family;
        this.raw = raw;
    }

    @Override
    public String getPrimaryKey() {
        if (raw.type == RedisObjectType.ARRAY &&
                raw.avalue != null &&
                raw.avalue.length > 1 &&
                raw.avalue[1].type == RedisObjectType.BULK) {
            return raw.avalue[1].bsvalue;
        }
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

    @Override
    public String toString() {
        return String.format("PrimaryKeyRedisCommand [mnemonic=%15s, type=%10s, family=%15s, key=%s]", mnemonic, type, family, getPrimaryKey());
    }
}
