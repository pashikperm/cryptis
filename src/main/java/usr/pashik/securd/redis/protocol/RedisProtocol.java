package usr.pashik.securd.redis.protocol;

import usr.pashik.securd.platform.bean.BeanedRunner;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.object.RedisObject;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 09.03.14 16:19.
 */
public class RedisProtocol {
    RedisCommandService commandService;

    protected Socket socket;
    protected RedisInputStream inputStream;
    protected RedisOutputStream outputStream;

    public RedisCommand readCommand() throws IOException, RedisProtocolReadException {
        reInject();
        RedisObject redisObject = inputStream.readObject();
        return commandService.getCommand(redisObject);
    }

    private void reInject() {
        if (commandService == null) {
            commandService = BeanedRunner.getWeldContainer().instance().select(RedisCommandService.class).get();
        }
    }

    public void sendCommand(RedisCommand command) throws IOException, RedisProtocolWriteException {
        outputStream.writeObject(command.getRaw());
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
