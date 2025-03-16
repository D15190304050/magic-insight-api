package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.magicinsight.domain.UserVideoComment;
import stark.magicinsight.dto.params.GetCommentsByVideoIdQueryParam;
import stark.magicinsight.dto.results.VideoCommentInfo;

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
