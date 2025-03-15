package stark.reshaper.magicinsight.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import stark.reshaper.magicinsight.services.JwtService;
import stark.reshaper.magicinsight.services.constants.SecurityConstants;
import stark.reshaper.magicinsight.services.dto.AccountPrincipal;
import stark.reshaper.magicinsight.services.redis.RedisKeyManager;

@Component
public class TokenLogoutHandler implements LogoutHandler
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisKeyManager redisKeyManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        // Remove cached roles and permissions.
        if (token != null)
        {
            AccountPrincipal accountPrincipal = jwtService.parseAccountPrincipal(token);
            if (accountPrincipal != null)
            {
                long accountId = accountPrincipal.getAccountId();
                redisTemplate.delete(redisKeyManager.getUserIdKey(accountId));
            }
        }
    }
}
