package com.core.hero.annotations;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
public class TimedAspectTest {

    @Test
    @DisplayName("Should execute some method with the timed annotation")
    public void shouldExecuteTimedAnnotation() {
        A target = new A();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        TimedAspect aspect = new TimedAspect();
        factory.addAspect(aspect);
        A proxy = factory.getProxy();
        assertDoesNotThrow(proxy::doSomething);
    }

    static class A {
        @Timed
        public void doSomething() {
            log.info("This is a test method");
        }
    }

}
