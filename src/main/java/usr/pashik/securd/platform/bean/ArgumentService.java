package usr.pashik.securd.platform.bean;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by pashik on 10.03.14 14:35.
 */
@ApplicationScoped
public class ArgumentService {
    private String[] args;

    public void setStartArguments(String[] args) {
        this.args = args;
    }

    public String getArgument(String key) {
        for (String arg : args) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                if (split[0].equals(key)) {
                    return split[1];
                }
            }
        }
        return null;
    }
}
