package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.NoKeyRedisCommand;
import usr.pashik.securd.redis.protocol.object.RedisObject;
import usr.pashik.securd.redis.protocol.object.RedisObjectFabric;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public class NoKeyRedisCommandFabric extends RedisCommandFabric {

    public NoKeyRedisCommandFabric(RedisCommandMnemonic mnemonic, RedisCommandType service, RedisCommandFamily connection) {
        this.mnemonic = mnemonic;
        this.type = service;
        this.family = connection;
    }

    @Override
    public RedisCommand build(RedisObject raw) {
        return new NoKeyRedisCommand(mnemonic, type, family, raw);
    }

    @Override
    public RedisCommand create(Object... args) {
        RedisObject commandName = RedisObjectFabric.getBulk(mnemonic.name());
        RedisObject command = RedisObjectFabric.getArray(new RedisObject[]{commandName});
        return new NoKeyRedisCommand(mnemonic, type, family, command);
    }
}
