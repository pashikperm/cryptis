package usr.pashik.securd.platform.commandengine;

import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by pashik on 10.03.14 1:47.
 */
public abstract class ServerCommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String execute(String[] args) throws Exception;

    @Inject
    ServerCommandService commandEngine;

    public void onContainerInitialized(@Observes ContainerInitialized initialized) {
        commandEngine.registerCommand(this);
    }
}
