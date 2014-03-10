package usr.pashik.securd.redis.protocol.response;

/**
 * Created by pashik on 10.03.14 23:04.
 */
public class RedisObject {
    public RedisObjectType type;

    public int ivalue;
    public String svalue;

    public byte[] bvalue;
    public String bsvalue;

    public RedisObject[] avalue;
}
