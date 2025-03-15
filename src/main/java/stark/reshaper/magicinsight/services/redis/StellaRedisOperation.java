package stark.reshaper.magicinsight.services.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.reshaper.magicinsight.services.JwtService;
import stark.reshaper.magicinsight.services.dto.User;

import java.util.concurrent.TimeUnit;

@Component
public class StellaRedisOperation
{
    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private RedisKeyManager redisKeyManager;

    public void cacheUser(User user)
    {
        long userId = user.getId();
        String userIdKey = redisKeyManager.getUserIdKey(userId);
        redisQuickOperation.set(userIdKey, user, JwtService.TOKEN_EXPIRATION_IN_DAYS, TimeUnit.DAYS);
    }
}
