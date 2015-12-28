package lunch.voting.core.aspects;

import lunch.voting.core.security.AccountUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Logging all calls to the restaurant service to facilitate user tech support.
 */
@Aspect
public class VotingLoggingAspect {

    private static Logger log = LoggerFactory.getLogger(VotingLoggingAspect.class);

    @Pointcut("execution(* lunch.voting.core.services.RestaurantService.*(..))")
    public void stub() {
    }

    @Before("stub()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("user: " + AccountUserDetails.getPrincipal() + "; method: " + joinPoint.getSignature().getName()
                + "; params: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing("stub()")
    public void logAfterException(JoinPoint joinPoint) {
        log.error("Exception for user: " + AccountUserDetails.getPrincipal() + "; method: " + joinPoint.getSignature().getName());
    }
}
