package usr.pashik.securd.platform.configurator.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.configurator.ConfiguratorService;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by pashik on 12.03.14 12:52.
 */
public class PrintConfigServerCommand extends ServerCommand {
    @Inject
    ConfiguratorService config;

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public String getDescription() {
        return "Prints all configuration fields";
    }

    @Override
    public String execute(String[] args) {
        StringBuilder result = new StringBuilder("Config fields:\n");
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length != 0) continue;
            if (!Modifier.isPublic(method.getModifiers())) continue;
            if (method.getName().equals("getTargetClass")) continue;
            if (method.getName().equals("getTargetInstance")) continue;
            if (method.getName().equals("getClass")) continue;
            if (method.getName().equals("getHandler")) continue;
            if (method.getName().equals("toString")) continue;
            if (method.getName().equals("hashCode")) continue;
            try {
                Object value = method.invoke(config);
                result.append(String.format("Config field [name=%30s, value=%15s]\n",
                                            method.getName(),
                                            value));
            } catch (Exception e) {
                result = new StringBuilder("Command error!");
            }
        }
        return result.toString();
    }
}
