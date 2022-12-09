package com.core.hero.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimedAspect {

    @Around("@annotation(com.core.hero.annotations.Timed)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        final var start = System.currentTimeMillis();
        final var proceed = joinPoint.proceed();
        final var total = System.currentTimeMillis() - start;

        log.info("Method {} in {}, had a duration of {}ms",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                total);

        return proceed;
    }

}