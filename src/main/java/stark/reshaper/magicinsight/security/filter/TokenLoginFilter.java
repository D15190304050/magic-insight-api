package stark.reshaper.magicinsight.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.boot.web.TokenHandler;
import stark.reshaper.magicinsight.services.JwtService;
import stark.reshaper.magicinsight.services.UserContextService;
import stark.reshaper.magicinsight.services.constants.SecurityConstants;
import stark.reshaper.magicinsight.services.dto.AccountPrincipal;
import stark.reshaper.magicinsight.services.dto.User;
import stark.reshaper.magicinsight.services.redis.RedisKeyManager;
import stark.reshaper.magicinsight.services.redis.StellaRedisOperation;

import java.io.IOException;

@Slf4j
public class TokenLoginFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;
    private final RedisQuickOperation redisQuickOperation;
    private final UserDetailsService userDetailsService;
    private final StellaRedisOperation stellaRedisOperation;
    private final RedisKeyManager redisKeyManager;

    public TokenLoginFilter(JwtService jwtService, RedisQuickOperation redisQuickOperation, UserDetailsService userDetailsService, StellaRedisOperation stellaRedisOperation, RedisKeyManager redisKeyManager)
    {
        this.jwtService = jwtService;
        this.redisQuickOperation = redisQuickOperation;
        this.userDetailsService = userDetailsService;
        this.stellaRedisOperation = stellaRedisOperation;
        this.redisKeyManager = redisKeyManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // Parse the login state if there is a token, no matter if it is a login state required Uri.

        String token = TokenHandler.getToken(request, SecurityConstants.SSO_COOKIE_NAME);

        if (StringUtils.hasText(token))
        {
            AccountPrincipal accountPrincipal = jwtService.parseAccountPrincipal(token);
            if (accountPrincipal != null)
            {
                long accountId = accountPrincipal.getAccountId();
                String userJson = redisQuickOperation.get(redisKeyManager.getUserIdKey(accountId));
                User user;

                if (StringUtils.hasText(userJson))
                    user = JsonSerializer.deserialize(userJson, User.class);
                else
                {
                    user = (User) userDetailsService.loadUserByUsername(accountPrincipal.getUsername());
                    stellaRedisOperation.cacheUser(user);
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                UserContextService.setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
