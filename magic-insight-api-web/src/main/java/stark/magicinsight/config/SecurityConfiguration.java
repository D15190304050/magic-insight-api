package stark.magicinsight.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.magicinsight.security.*;
import stark.magicinsight.security.filter.TokenLoginFilter;
import stark.magicinsight.security.filter.UsernamePasswordLoginFilter;
import stark.magicinsight.service.DaoUserDetailService;
import stark.magicinsight.service.JwtService;
import stark.magicinsight.service.constants.SecurityConstants;
import stark.magicinsight.service.redis.RedisKeyManager;
import stark.magicinsight.service.redis.MagicRedisOperation;

@Slf4j
@Configuration
public class SecurityConfiguration
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private LoginSuccessJsonHandler loginSuccessJsonHandler;

    @Autowired
    private LoginFailureJsonHandler loginFailureJsonHandler;

    @Autowired
    private LogoutSuccessJsonHandler logoutSuccessJsonHandler;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private DaoUserDetailService daoUserDetailService;

    @Autowired
    private MagicRedisOperation magicRedisOperation;

    @Autowired
    private RedisKeyManager redisKeyManager;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    public UsernamePasswordLoginFilter usernamePasswordLoginFilter() throws Exception
    {
        UsernamePasswordLoginFilter loginFilter = new UsernamePasswordLoginFilter();
        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        loginFilter.setAuthenticationSuccessHandler(loginSuccessJsonHandler); // Handler for authentication success.
        loginFilter.setAuthenticationFailureHandler(loginFailureJsonHandler); // Handler for authentication failure.
        loginFilter.setFilterProcessesUrl(SecurityConstants.DEFAULT_LOGIN_URI);
        return loginFilter;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(request ->
            {
                request.requestMatchers(SecurityConstants.NON_AUTHENTICATE_URIS).permitAll();
                request.anyRequest().authenticated();
            })
            .rememberMe(customizer -> customizer.alwaysRemember(true))
            .exceptionHandling(customizer ->
            {
                customizer.authenticationEntryPoint(new UnauthorizedEntryPoint());
                customizer.accessDeniedHandler(new NoPermissionHandler());
            })
            .formLogin(customizer ->
            {
                customizer.successHandler(loginSuccessJsonHandler);
                customizer.failureHandler(loginFailureJsonHandler);
            })
            .logout(customizer ->
            {
                customizer.logoutUrl(SecurityConstants.DEFAULT_LOGOUT_URI);
                customizer.logoutSuccessHandler(logoutSuccessJsonHandler);
            })
            .cors(customizer -> customizer.configurationSource(corsConfigurationSource))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new TokenLoginFilter(jwtService, redisQuickOperation, daoUserDetailService, magicRedisOperation, redisKeyManager), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(usernamePasswordLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
