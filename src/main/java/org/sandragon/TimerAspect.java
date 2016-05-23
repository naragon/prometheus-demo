package org.sandragon;

import io.prometheus.client.Histogram;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class TimerAspect {

	private ConcurrentHashMap<String, Histogram> registered = new ConcurrentHashMap<>();

   	 @Around("@annotation(Timed)")
  	 public Object logTimed(ProceedingJoinPoint joinPoint) throws Throwable {

	    Timed timed = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(Timed.class);
        String name = timed.name();
        Histogram histogram = registered.computeIfAbsent(name,
                s -> Histogram.build().name(name).help(timed.help()).register());
        Histogram.Timer timer = histogram.startTimer();

        try {
            return joinPoint.proceed();
        } finally {
            timer.observeDuration();
        }
   }
	
}