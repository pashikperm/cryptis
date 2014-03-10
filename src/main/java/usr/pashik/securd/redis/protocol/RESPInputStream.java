package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.redis.protocol.exception.ProtocolParseException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pashik on 09.03.14 16:19.
 * Thanks to Jedis!
 */
public class RESPInputStream extends BufferedInputStream {
    final byte STRING_PREFIX = '+';
    final byte ERROR_PREFIX = '-';
    final byte INTEGER_PREFIX = ':';
    final byte BULK_PREFIX = '$';
    final byte ARRAY_PREFIX = '*';
    final byte R = '\r';
    final byte N = '\n';

    public RESPInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException, ProtocolParseException {
        return readRawString();
    }

    public String readError() throws IOException, ProtocolParseException {
        return readRawString();
    }

    public int readInteger() throws IOException, ProtocolParseException {
        return Integer.parseInt(readRawString());
    }

    public String readBulkString() throws IOException, ProtocolParseException {
        return new String(readBulkStringRaw());
    }

    public byte[] readBulkStringRaw() throws IOException, ProtocolParseException {
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

    public Object[] readArray() throws IOException, ProtocolParseException {
        int length = readInteger();
        if (length == -1) {
            verifyTerminator();
            return null;
        }
        Object[] result = new Object[length];
        for (int i = 0; i < length; i++) {
            result[i] = readObject();
        }
        return result;
    }

    public Object readObject() throws IOException, ProtocolParseException {
        int type = read();
        switch (type) {
            case STRING_PREFIX:
                return readString();
            case ERROR_PREFIX:
                return readError();
            case INTEGER_PREFIX:
                return readInteger();
            case BULK_PREFIX:
                return readBulkString();
            case ARRAY_PREFIX:
                return readArray();
            default:
                throw new ProtocolParseException();
        }
    }

    private String readRawString() throws IOException, ProtocolParseException {
        StringBuffer result = new StringBuffer();
        int b;
        while ((b = read()) != R) {
            result.appendCodePoint(b);
        }
        b = read();
        if (b != N) {
            throw new ProtocolParseException();
        }
        return result.toString();
    }

    private void verifyTerminator() throws IOException, ProtocolParseException {
        int b = read();
        if (b != R) {
            throw new ProtocolParseException();
        }
        b = read();
        if (b != N) {
            throw new ProtocolParseException();
        }
    }
}
