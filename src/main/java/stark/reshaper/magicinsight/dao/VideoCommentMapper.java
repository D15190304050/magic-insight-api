package stark.reshaper.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.reshaper.magicinsight.domain.dtos.params.GetCommentsByVideoIdQueryParam;
import stark.reshaper.magicinsight.domain.dtos.results.VideoCommentInfo;
import stark.reshaper.magicinsight.domain.entities.UserVideoComment;

import java.util.List;

@Mapper
public interface VideoCommentMapper
{
    int countByParentId(long parentId);
    int insertComment(UserVideoComment userVideoComment);
    List<VideoCommentInfo> getVideoCommentsByVideoId(GetCommentsByVideoIdQueryParam queryParam);
    int deleteCommentById(long id);
    UserVideoComment getCommentById(long id);
    long countCommentsByVideoId(long videoId);
}
