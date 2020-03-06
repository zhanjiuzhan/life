package org.jcl.life.initialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author chenglei
 */
@Component
public class BeanInitializationExecutor implements BeanPostProcessor {
    private static Logger logger =
            LoggerFactory.getLogger(BeanInitializationExecutor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        if (beanClassName.contains("org.jcl.life")) {
            logger.info("Bean 初始化完成: [{}]", beanClassName);
        }
        return bean;
    }
}
