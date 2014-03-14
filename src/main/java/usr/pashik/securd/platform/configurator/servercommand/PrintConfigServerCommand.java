package usr.pashik.securd.platform.configurator.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.configurator.ConfiguratorService;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    public String execute(String[] args) throws InvocationTargetException, IllegalAccessException {
        final List<String> prohibitedParts = new LinkedList<String>(){{
            add("getTargetClass");
            add("getTargetInstance");
            add("getClass");
            add("getHandler");
            add("toString");
            add("hashCode");
            add("$");
        }};

        StringBuilder result = new StringBuilder("Config fields:\n");
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length != 0) continue;
            if (!Modifier.isPublic(method.getModifiers())) continue;
            if (method.getParameterAnnotations().length != 0) continue;
            if (method.getDeclaringClass() != config.getClass()) continue;

            String methodName = method.getName();
            boolean containsProhibitedParts = false;
            for (String part : prohibitedParts) {
                if (methodName.contains(part)) {
                    containsProhibitedParts = true;
                    break;
                }
            }
            if (containsProhibitedParts) continue;

            Object value = method.invoke(config);
            result.append(String.format("Config field [name=%30s, value=%15s]\n",
                                        methodName,
                                        value != null ? value : "null"));
        }
        return result.toString();
    }
}
