package com.core.hero.annotations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimedProcessorTest {

    @Test
    @DisplayName("Should always return true")
    public void shouldAlwaysReturnTrue() {
        final var timedProcessor = new TimedProcessor();
        assertTrue(timedProcessor.process(null, null));
        assertTrue(timedProcessor.process(new HashSet<>(), null));
        assertTrue(timedProcessor.process(null, this.getMockEnvironment()));
        assertTrue(timedProcessor.process(new HashSet<>(), this.getMockEnvironment()));
    }

    private RoundEnvironment getMockEnvironment() {
        return new RoundEnvironment() {
            @Override
            public boolean processingOver() {
                return false;
            }

            @Override
            public boolean errorRaised() {
                return false;
            }

            @Override
            public Set<? extends Element> getRootElements() {
                return null;
            }

            @Override
            public Set<? extends Element> getElementsAnnotatedWith(TypeElement a) {
                return null;
            }

            @Override
            public Set<? extends Element> getElementsAnnotatedWith(Class<? extends Annotation> a) {
                return null;
            }
        };
    }
}
