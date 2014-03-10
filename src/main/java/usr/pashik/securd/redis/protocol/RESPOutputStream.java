package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.redis.protocol.exception.ProtocolWriteException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;

/**
 * Created by pashik on 09.03.14 16:19.
 * Thanks to Jedis!
 */

/**
 * The class implements a buffered output stream without synchronization There
 * are also special operations like in-place string encoding. This stream fully
 * ignore mark/reset and should not be used outside Jedis
 */
public final class RESPOutputStream extends BufferedOutputStream {
    final byte STRING_PREFIX = '+';
    final byte ERROR_PREFIX = '-';
    final byte INTEGER_PREFIX = ':';
    final byte BULK_PREFIX = '$';
    final byte ARRAY_PREFIX = '*';
    final byte R = '\r';
    final byte N = '\n';
    final byte[] TERMINATOR = {'\r', '\n'};

    public RESPOutputStream(OutputStream out) {
        super(out);
    }

    public void writeString(String value) throws IOException {
        write(STRING_PREFIX);
        writeRawString(value);
    }

    public void writeError(String value) throws IOException {
        write(ERROR_PREFIX);
        writeRawString(value);
    }

    public void writeInteger(int value) throws IOException {
        write(INTEGER_PREFIX);
        writeRawString(Integer.toString(value));
    }

    public void writeBulkString(String value) throws IOException {
        writeBulkStringRaw(value.getBytes());
    }

    public void writeBulkStringRaw(byte[] value) throws IOException {
        write(BULK_PREFIX);
        if (value == null) {
            writeInteger(-1);
            return;
        }
        int length = value.length;
        writeInteger(length);
        write(value);
        writeTerminator();
    }

    public void writeArray(Object[] value) throws IOException, ProtocolWriteException {
        write(ARRAY_PREFIX);
        writeInteger(value.length);
        for (int i = 0; i < value.length; i++) {
            writeObject(value);
        }
        writeTerminator();
    }

    public void writeObject(Object value) throws IOException, ProtocolWriteException {
        if (value instanceof String) {
            writeString((String) value);
        } else if (value instanceof Integer) {
            writeInteger((Integer) value);
        } else if (value instanceof Array) {
            writeArray((Object[]) value);
        } else if (value == null) {
            writeBulkStringRaw(null);
        } else {
            throw new ProtocolWriteException();
        }
    }

    private void writeRawString(String value) throws IOException {
        write(value.getBytes());
        writeTerminator();
    }

    public void writeTerminator() throws IOException {
        write(TERMINATOR);
    }
}
