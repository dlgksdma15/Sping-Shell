package com.example.demo.price.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class PriceAop {

    @Pointcut("within(com.example.demo.shell.MyCommands)" +
            "&& !execution(* com.example.demo.shell.MyCommands.login(..))" +
            "&& !execution(* com.example.demo.shell.MyCommands.logout())")
//            "&& !execution(* com.example.demo.shell.MyCommands.currentUser())")
    public void shellCut() {
    }

    @Around("shellCut()")
    public Object doShell(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("----- 이한음 class {}.{}({}) ----->", className, methodName, Arrays.toString(args));

        Object retVal = pjp.proceed();

        log.info("<----- 이한음 class {}.{}({}) -----", className, methodName, retVal);

        return retVal;
    }
}