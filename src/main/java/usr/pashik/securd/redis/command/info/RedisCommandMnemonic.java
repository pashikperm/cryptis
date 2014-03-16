package usr.pashik.securd.redis.command.info;

/**
 * Created by pashik on 11.03.14 23:45.
 */
public enum RedisCommandMnemonic {
    // KEYS
//    DEL, DUMP, EXISTS, EXPIRE, EXPIREAT, KEYS, MIGRATE,
//    MOVE, OBJECT, PERSIST, PEXPIRE, REXPIREAT, PTTL,
//    RANDOMKEY, RENAME, RENAMENX, RESTORE, SORT, TTL, SCAN,
    RANDOMKEY,
    // STRINGS
    GET, SET, STRLEN,
    // HASHES
    // LISTS
    // SETS
    // SORTED SETS
    // PUB/SUB
    // TRANSACTIONS
    // SCRIPTING
    // CONNECTION
    PING, AUTH, ECHO,
    // SERVER
    // UNKNOWN
    UNKNOWN
}
