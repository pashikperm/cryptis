package usr.pashik.securd.redis.protocol.object;

/**
 * Created by pashik on 14.03.14 22:19.
 */
public class RedisObjectFabric {
    public static final String CREDENTIALS_ERROR = "ERR invalid password";
    public static final String ACCESS_EXCEPTION = "ERR operation not permitted";
    public static final String SUCCESS_AUTH = "OK";

    public static RedisObject getSimpleString(String message) {
        RedisObject response = new RedisObject();
        response.type = RedisObjectType.STRING;
        response.svalue = message;
        return response;
    }

    public static RedisObject getError(String message) {
        RedisObject response = new RedisObject();
        response.type = RedisObjectType.ERROR;
        response.svalue = message;
        return response;
    }

    public static RedisObject getInteger(int message) {
        RedisObject response = new RedisObject();
        response.type = RedisObjectType.INTEGER;
        response.ivalue = message;
        return response;
    }

    public static RedisObject getBulk(String message) {
        RedisObject response = new RedisObject();
        response.type = RedisObjectType.BULK;
        if (message != null) {
            response.bsvalue = message;
            response.bvalue = message.getBytes();
        }
        return response;
    }

    public static RedisObject getArray(RedisObject[] objects) {
        RedisObject response = new RedisObject();
        response.type = RedisObjectType.ARRAY;
        response.avalue = objects;
        return response;
    }
}
