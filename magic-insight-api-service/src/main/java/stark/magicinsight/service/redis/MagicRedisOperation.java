package stark.magicinsight.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.magicinsight.service.JwtService;
import stark.magicinsight.service.dto.User;

import java.util.concurrent.TimeUnit;

@Component
public class MagicRedisOperation
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
