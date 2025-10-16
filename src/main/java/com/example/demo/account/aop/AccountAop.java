package com.example.demo.account.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AccountAop {

    @Pointcut("execution(* com.example.demo.account.service.AuthenticationService.login(..)) " +
            "|| execution(* com.example.demo.account.service.AuthenticationService.logout()))")
    public void accountCut(){
    }

    @Before("accountCut()")
    public void doAccountSimpleLog(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName(); // 메서드 이름
        Object[] args = joinPoint.getArgs(); // 메서드 인자

        log.info("{} ({})", methodName, args);
    }
}