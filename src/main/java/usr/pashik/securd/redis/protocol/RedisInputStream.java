package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.redis.protocol.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pashik on 09.03.14 16:19.
 */
public class RedisInputStream extends BufferedInputStream {
    final byte STRING_PREFIX = '+';
    final byte ERROR_PREFIX = '-';
    final byte INTEGER_PREFIX = ':';
    final byte BULK_PREFIX = '$';
    final byte ARRAY_PREFIX = '*';
    final byte R = '\r';
    final byte N = '\n';

    public RedisInputStream(InputStream in) {
        super(in);
    }

    private int readInteger() throws IOException, RedisProtocolReadException {
        return Integer.parseInt(readRawString());
    }

    private byte[] readBulkRaw() throws IOException, RedisProtocolReadException {
        int length = readInteger();
        if (length == -1) {
            verifyTerminator();
            return null;
        }
        byte[] result = new byte[length];
        read(result);
        verifyTerminator();
        return result;
    }

    private RedisObject[] readArray() throws IOException, RedisProtocolReadException {
        int length = readInteger();
        if (length == -1) {
            verifyTerminator();
            return null;
        }
        RedisObject[] result = new RedisObject[length];
        for (int i = 0; i < length; i++) {
            result[i] = readObject();
        }
        return result;
    }

    public RedisObject readObject() throws IOException, RedisProtocolReadException {
        RedisObject result = new RedisObject();
        int type = read();
        switch (type) {
            case STRING_PREFIX:
                result.type = RedisObjectType.STRING;
                result.svalue = readRawString();
                break;
            case ERROR_PREFIX:
                result.type = RedisObjectType.ERROR;
                result.svalue = readRawString();
                break;
            case INTEGER_PREFIX:
                result.type = RedisObjectType.INTEGER;
                result.svalue = readRawString();
                result.ivalue = Integer.parseInt(result.svalue);
                break;
            case BULK_PREFIX:
                result.type = RedisObjectType.BULK;
                result.bvalue = readBulkRaw();
                result.bsvalue = new String(result.bvalue);
                break;
            case ARRAY_PREFIX:
                result.type = RedisObjectType.ARRAY;
                result.avalue = readArray();
                break;
            default:
                throw new RedisProtocolReadException();
        }
        return result;
    }

    private String readRawString() throws IOException, RedisProtocolReadException {
        StringBuffer result = new StringBuffer();
        int b;
        while ((b = read()) != R) {
            result.appendCodePoint(b);
        }
        b = read();
        if (b != N) {
            throw new RedisProtocolReadException();
        }
        return result.toString();
    }

    private void verifyTerminator() throws IOException, RedisProtocolReadException {
        int b = read();
        if (b != R) {
            throw new RedisProtocolReadException();
        }
        b = read();
        if (b != N) {
            throw new RedisProtocolReadException();
        }
    }
}
