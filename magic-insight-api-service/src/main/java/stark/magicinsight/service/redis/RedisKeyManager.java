package stark.magicinsight.service.redis;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import stark.dataworks.boot.autoconfig.web.LogRedisKeys;
import stark.magicinsight.service.constants.RedisKeyPrefixes;

@LogRedisKeys
@Component
@NoArgsConstructor
public class RedisKeyManager
{
    public String getUserIdKey(long accountId)
    {
        return RedisKeyPrefixes.USER + accountId;
    }

    public String getVideoPlayUrlKey(long videoId)
    {
        return RedisKeyPrefixes.VIDEO_PLAY_URL + videoId;
    }

    public String getMarkedVideoPlayUrlKey(long videoId)
    {
        return RedisKeyPrefixes.MARKED_VIDEO_PLAY_URL + videoId;
    }

}
