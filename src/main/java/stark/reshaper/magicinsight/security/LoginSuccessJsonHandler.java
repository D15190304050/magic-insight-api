package stark.reshaper.magicinsight.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.magicinsight.domain.dtos.results.LoginStateToken;
import stark.reshaper.magicinsight.services.JwtService;
import stark.reshaper.magicinsight.services.constants.SecurityConstants;
import stark.reshaper.magicinsight.services.dto.User;
import stark.reshaper.magicinsight.services.redis.StellaRedisOperation;

import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessJsonHandler implements AuthenticationSuccessHandler
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private StellaRedisOperation redisOperation;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        User user = (User) authentication.getPrincipal();
        cacheAuthentication(user);
        writeAuthenticationToken(request, response, user);
    }

    public String prepareUserLoginInfoToken(User user)
    {
        return "";
    }

    // For SSO, we only need to return a token.
    // Then other system can get user info like username by token.
    // Without SSO, we can return all the information.
    public void writeAuthenticationToken(HttpServletRequest request, HttpServletResponse response, User user) throws IOException
    {
        Object redirectUrlAttribute = request.getAttribute(SecurityConstants.REDIRECT_URL);
        String redirectUrl = redirectUrlAttribute == null ? null : (String) redirectUrlAttribute;
        log.info("redirectUrl = {}", redirectUrl);

        LoginStateToken loginStateToken = generateLoginStateToken(user);
        ServiceResponse<LoginStateToken> loginSuccessResponse = ServiceResponse.buildSuccessResponse(loginStateToken, SecurityConstants.LOGIN_SUCCESS);
        loginSuccessResponse.writeToResponse(response);
    }

    // TODO: Move this method to another class after integration of other login methods.
    private void cacheAuthentication(User user)
    {
        // Cache user info.
        redisOperation.cacheUser(user);
    }

    private LoginStateToken generateLoginStateToken(User user)
    {
        String token = jwtService.createToken(user);
        LoginStateToken loginStateToken = new LoginStateToken();
        loginStateToken.setAccessToken(token);
        return loginStateToken;
    }
}
