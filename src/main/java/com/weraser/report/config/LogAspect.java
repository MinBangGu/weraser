package com.weraser.report.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	@Before("execution(* com..controller.*Controller.*(..))")
	public void beforeLog(JoinPoint joinPoint) {
		
		System.out.println("Before");
	}
	
	@AfterReturning(pointcut="execution(* com..controller.*Controller.*(..))", returning = "returnVal")
	public void afterLog(JoinPoint joinPoint, Object returnVal) {
		
		System.out.println("After");
		
	}
	
	@Around("execution(* com..controller.*Controller.*(..))")
	public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		Object result = proceedingJoinPoint.proceed();
		
		System.out.println("Around");
		
		return result;
	}
}
