package usr.pashik.securd.platform.thread;

import usr.pashik.securd.platform.bean.BeanedRunner;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

/**
 * Created by pashik on 10.03.14 13:55.
 */
public class BeanInjector {
    public static <T> T injectFields(BeanManager beanManager, T instance) {
        CreationalContext creationalContext = beanManager.createCreationalContext(null);
        AnnotatedType annotatedType = beanManager.createAnnotatedType(instance.getClass());
        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
        injectionTarget.inject(instance, creationalContext);
        return instance;
    }

    public static <T> T injectFields(T instance) {
        return injectFields(BeanedRunner.getBeanManager(), instance);
    }

}
