package stark.reshaper.magicinsight.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.magicinsight.services.constants.SecurityConstants;

import java.io.IOException;

@Slf4j
@Component
public class LogoutSuccessJsonHandler implements LogoutSuccessHandler
{
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        ServiceResponse<Boolean> logoutResponse = ServiceResponse.buildSuccessResponse(true, SecurityConstants.LOGOUT_SUCCESS);
        logoutResponse.writeToResponse(response);
    }
}
