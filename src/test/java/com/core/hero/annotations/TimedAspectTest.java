package com.core.hero.annotations;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class TimedAspectTest {

    @Test
    @DisplayName("Should execute some method with the timed annotation")
    public void shouldExecuteTimedAnnotation() {
        final var target = new A();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        TimedAspect aspect = new TimedAspect();
        factory.addAspect(aspect);
        final A proxy = factory.getProxy();
        assertDoesNotThrow(proxy::doSomething);
        assertDoesNotThrow(() -> proxy.exampleMethod(15));
        assertThrows(NullPointerException.class, proxy::throwableMethod);
    }

    static class A {
        @Timed
        public void doSomething() {
            log.info("This is a test method");
        }

        @Timed
        public int exampleMethod(int total) throws InterruptedException {
            Thread.sleep(1);
            if(total <= 1) {
                return 1;
            }
            return total - 1;
        }

        @Timed
        public void throwableMethod() {
            throw new NullPointerException();
        }
    }

}
