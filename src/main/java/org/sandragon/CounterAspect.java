
package org.sandragon;

import io.prometheus.client.Counter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class CounterAspect {

   private final Logger log = LoggerFactory.getLogger(this.getClass());
	
   private Map<String, Counter> registered = new ConcurrentHashMap<String, Counter>();

   @Around("@annotation(Counted)")
   public Object logCounted(ProceedingJoinPoint joinPoint) throws Throwable {

   		log.info("Intercepting @Counted method ...");
   		Counted counted = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(Counted.class);
    	String name = counted.name();
    	Counter counter = registered.computeIfAbsent(name, s -> Counter.build().name(name).help(counted.help()).register());
		try {
			return joinPoint.proceed();
		} finally {
			counter.inc();
			log.info("@Counted method executed");
		}
   }
	
}