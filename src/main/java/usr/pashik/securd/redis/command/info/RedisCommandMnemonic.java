package usr.pashik.securd.redis.command.info;

/**
 * Created by pashik on 11.03.14 23:45.
 */
public enum RedisCommandMnemonic {
    // KEYS
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
