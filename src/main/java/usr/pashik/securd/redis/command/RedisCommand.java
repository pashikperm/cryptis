package usr.pashik.securd.redis.command;

import usr.pashik.securd.redis.command.meta.RedisCommandFamily;
import usr.pashik.securd.redis.command.meta.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

/**
 * Created by pashik on 10.03.14 17:20.
 */
public abstract class RedisCommand {
    public abstract RedisCommandType getType();

    public abstract RedisCommandFamily getFamily();

    protected RedisObject raw;

    public RedisObject getRaw() {
        return raw;
    }

    public String getName() {
        if (raw.type == RedisObjectType.ARRAY &&
                raw.avalue != null &&
                raw.avalue.length > 0 &&
                raw.avalue[0].type == RedisObjectType.BULK) {
            return raw.avalue[0].bsvalue;
        }
        return null;
    }

    public String getPrimaryKey() {
        if (raw.type == RedisObjectType.ARRAY &&
                raw.avalue != null &&
                raw.avalue.length > 1 &&
                raw.avalue[1].type == RedisObjectType.BULK) {
            return raw.avalue[1].bsvalue;
        }
        return null;
    }

    public String getSecondaryKey() {
        if (raw.type == RedisObjectType.ARRAY &&
                raw.avalue != null &&
                raw.avalue.length > 2 &&
                raw.avalue[2].type == RedisObjectType.BULK) {
            return raw.avalue[2].bsvalue;
        }
        return null;
    }

    public RedisObject getArguments() {
        if (raw.type == RedisObjectType.ARRAY &&
                raw.avalue != null &&
                raw.avalue.length > 1) {
            return raw.avalue[1];
        }
        return null;
    }

}
