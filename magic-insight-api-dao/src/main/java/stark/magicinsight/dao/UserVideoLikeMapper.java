package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.magicinsight.domain.entities.UserVideoLike;

@Mapper
public interface UserVideoLikeMapper
{
    long countUserVideoLike(long userId, long videoId);
    int insertLike(UserVideoLike userVideoLike);
    int deleteLike(long userId, long videoId);
}
