package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.NoKeyRedisCommand;
import usr.pashik.securd.redis.command.meta.command.PrimaryKeyRedisCommand;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public class PrimaryKeyRedisCommandFabric extends RedisCommandFabric {

    public PrimaryKeyRedisCommandFabric(RedisCommandMnemonic mnemonic, RedisCommandType service, RedisCommandFamily connection) {
        this.mnemonic = mnemonic;
        this.type = service;
        this.family = connection;
    }

    @Override
    public RedisCommand build(RedisObject raw) {
        return new PrimaryKeyRedisCommand(mnemonic, type, family, raw);
    }

    @Override
    public RedisCommand create(Object... args) {
        RedisObject command = new RedisObject();
        RedisObject commandName = new RedisObject();
        RedisObject commandKey = new RedisObject();

        command.type = RedisObjectType.ARRAY;
        command.avalue = new RedisObject[2];
        command.avalue[0] = commandName;
        command.avalue[1] = commandKey;

        commandName.type = RedisObjectType.BULK;
        commandName.bsvalue = mnemonic.name();
        commandName.bvalue = commandName.bsvalue.getBytes();

        commandKey.type = RedisObjectType.BULK;
        commandKey.bsvalue = (String) args[0];
        commandKey.bvalue = commandKey.bsvalue.getBytes();

        return new PrimaryKeyRedisCommand(mnemonic, type, family, command);
    }
}
