package liga.medical.messageanalyzer.core.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.commonmodule.core.api.LogService;
import liga.medical.dto.dto.DebugDto;
import liga.medical.dto.dto.ExceptionDto;
import liga.medical.dto.enums.SystemType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {

    private final LogService logService;

    public LogAdvice(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(liga.medical.dto.annotations.DbLog)")
    public void loggableMethods() { }

    @Around("loggableMethods()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] args = pjp.getArgs();

        Object object = null;

        String methodParams = "Вызван метод " + className + ": " + methodName + "() " + "с аргументами: " + objectMapper.writeValueAsString(args);

        try {
            object = pjp.proceed();
        } catch (Throwable e) {
            logService.logException(new ExceptionDto(SystemType.MESSAGE_ANALYZER, methodParams));
            return object;
        }
        logService.logDebug(new DebugDto(SystemType.MESSAGE_ANALYZER, methodParams));
        return object;
    }
}
