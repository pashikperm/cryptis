package usr.pashik.securd.bean;

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
}
