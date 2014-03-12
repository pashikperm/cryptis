package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.redis.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by pashik on 09.03.14 16:19.
 */
public class RedisOutputStream extends BufferedOutputStream {
    final byte STRING_PREFIX = '+';
    final byte ERROR_PREFIX = '-';
    final byte INTEGER_PREFIX = ':';
    final byte BULK_PREFIX = '$';
    final byte ARRAY_PREFIX = '*';
    final byte[] TERMINATOR = {'\r', '\n'};

    public RedisOutputStream(OutputStream out) {
        super(out);
    }

    private void writeInteger(int value) throws IOException {
        writeRawString(Integer.toString(value));
    }

    private void writeBulkRaw(byte[] value) throws IOException {
        write(BULK_PREFIX);
        if (value == null) {
            writeInteger(-1);
            return;
        }
        int length = value.length;
        writeInteger(length);
        write(value);
        write(TERMINATOR);
    }

    private void writeArray(RedisObject[] value) throws IOException, RedisProtocolWriteException {
        write(ARRAY_PREFIX);
        writeInteger(value.length);
        for (int i = 0; i < value.length; i++) {
            writeObject(value[i]);
        }
    }

    public void writeObject(RedisObject value) throws IOException, RedisProtocolWriteException {
        switch (value.type) {
            case STRING:
                write(STRING_PREFIX);
                writeRawString(value.svalue);
                break;
            case ERROR:
                write(ERROR_PREFIX);
                writeRawString(value.svalue);
                break;
            case INTEGER:
                write(INTEGER_PREFIX);
                writeRawString(value.svalue);
                break;
            case BULK:
                writeBulkRaw(value.bvalue);
                break;
            case ARRAY:
                writeArray(value.avalue);
                break;
            default:
                throw new RedisProtocolWriteException();
        }
    }

    private void writeRawString(String value) throws IOException {
        write(value.getBytes());
        write(TERMINATOR);
    }
}
