package stark.reshaper.magicinsight.configurations;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import stark.dataworks.boot.ExceptionLogger;
import stark.dataworks.boot.web.ServiceResponse;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class})
    public ServiceResponse<?> handleValidationException(Exception e)
    {
        ExceptionLogger.logExceptionInfo(e);
        return ServiceResponse.buildErrorResponse(-100, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ServiceResponse<?> handleException(Exception e)
    {
        ExceptionLogger.logExceptionInfo(e);
        return ServiceResponse.buildErrorResponse(-200, e.getMessage());
    }
}
