package usr.pashik.securd.redis.command.meta.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

/**
 * Created by pashik on 12.03.14 0:37.
 */
public class RedisCommandListServerCommand extends ServerCommand {
    @Inject
    RedisCommandService commandService;

    @Override
    public String getName() {
        return "rcommands";
    }

    @Override
    public String execute(String[] args) {
        Collection<RedisCommandFabric> fabrics = commandService.getRegistered();
        StringBuilder result = new StringBuilder("Supported Redis commands:\n");
        for (RedisCommandFabric fabric : fabrics) {
            result.append(fabric);
            result.append("\n");
        }
        return result.toString();
    }
}
