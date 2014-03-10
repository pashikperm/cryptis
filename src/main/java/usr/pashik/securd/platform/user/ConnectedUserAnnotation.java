package usr.pashik.securd.platform.user;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by pashik on 10.03.14 1:02.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, TYPE, METHOD})
@Qualifier
public @interface ConnectedUserAnnotation {
}
