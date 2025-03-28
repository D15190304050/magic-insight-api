package stark.magicinsight.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import stark.dataworks.boot.web.CommonErrorResponses;
import stark.dataworks.boot.web.ServiceResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException
    {
        ServiceResponse<Object> serviceResponse = ServiceResponse.buildErrorResponse(CommonErrorResponses.NOT_LOGIN);
        serviceResponse.writeToResponse(response);
    }
}
