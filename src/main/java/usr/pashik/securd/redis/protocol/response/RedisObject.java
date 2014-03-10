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

    @Override
    public String toString() {
        switch (type) {
            case STRING:
                return "+" + svalue + "\n";
            case ERROR:
                return "-" + svalue + "\n";
            case INTEGER:
                return ":" + ivalue + "\n";
            case BULK:
                return "$" + bsvalue.length() + bsvalue + "\n";
            case ARRAY:
                StringBuilder result = new StringBuilder("*" + avalue.length + "\n");
                for (RedisObject redisObject : avalue) {
                    result.append(redisObject);
                }
                return result.toString();
            default:
                return "!!!non parsed!!!";
        }
    }
}
