package com.consertreservation.common.aop;

import com.consertreservation.common.aop.annotation.Retry;
import jakarta.persistence.OptimisticLockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
public class OptimisticLockRetryAspect {

    @Around("@annotation(retry)")
    public Object retryOptimisticLock(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        Exception exceptionHolder = null;
        for (int attempt = 0; attempt < retry.maxRetries(); attempt++) {
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException | StaleObjectStateException e) {
                exceptionHolder = e;
                Thread.sleep(retry.retryDelay());
            }
        }
        throw exceptionHolder;
    }
}
