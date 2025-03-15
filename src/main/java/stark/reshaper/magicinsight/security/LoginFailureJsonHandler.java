package stark.reshaper.magicinsight.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.exceptions.ExceptionInfoFormatter;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.magicinsight.domain.dtos.results.LoginStateToken;

import java.io.IOException;

@Slf4j
@Component
public class LoginFailureJsonHandler implements AuthenticationFailureHandler
{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException
    {
        String exceptionInfo = ExceptionInfoFormatter.formatMessageAndStackTrace(exception);
        log.error("Login failure: {}", exceptionInfo);

        ServiceResponse<LoginStateToken> loginResult = ServiceResponse.buildErrorResponse(-1, exceptionInfo);
        loginResult.writeToResponse(response);
    }
}
