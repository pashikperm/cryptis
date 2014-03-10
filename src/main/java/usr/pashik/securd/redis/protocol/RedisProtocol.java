package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.UnknownCommand;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 09.03.14 16:19.
 */
public class RedisProtocol {
    protected Socket socket;
    protected RedisInputStream inputStream;
    protected RedisOutputStream outputStream;

    public RedisCommand readCommand() throws IOException, RedisProtocolReadException {
        RedisObject redisObject = inputStream.readObject();
        RedisCommand command = new UnknownCommand(redisObject);
        return command;
    }

    public void sendCommand(RedisCommand command) throws IOException, RedisProtocolWriteException {
        outputStream.writeObject(command.getInnerRepresentation());
        outputStream.flush();
    }

    public RedisObject readResponse() throws IOException, RedisProtocolReadException {
        return inputStream.readObject();
    }

    public void sendResponse(RedisObject response) throws IOException, RedisProtocolWriteException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
