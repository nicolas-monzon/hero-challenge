package com.core.hero.config;

import com.core.hero.annotations.Timed;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTimingBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> map;

    @PostConstruct
    public void postConstruct() {
        this.map = new HashMap<>();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Timed.class)) {
            map.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if(map.containsKey(beanName)) {
            Class<?> clazz = this.map.get(beanName);
            return Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    clazz.getInterfaces(),
                    (proxy, method, args) -> {
                        long startAt = System.currentTimeMillis();
                        Object invoke = method.invoke(bean, args);
                        long total = System.currentTimeMillis() - startAt;

                        System.out.printf("Total method %s duration: %d", method.getName(), total);
                        return invoke;
                    }
            );
        }
        return bean;
    }

}
