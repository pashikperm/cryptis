package usr.pashik.securd.redis.command.info;

/**
 * Created by pashik on 11.03.14 23:45.
 */
public enum RedisCommandMnemonic {
    // KEYS
    DEL, DUMP, EXISTS, KEYS, OBJECT, RANDOMKEY, RENAME, SORT, TYPE,
    // STRINGS
    APPEND, DECR, DECRBY, GET, GETRANGE, GETSET,
    INCR, INCRBY, MGET, MSET, SET, STRLEN,
    // HASHES
    HDEL, HEXISTS, HGET, HGETALL, HKEYS, HLEN, HMGET, HMSET, HSET, HVALS,
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
