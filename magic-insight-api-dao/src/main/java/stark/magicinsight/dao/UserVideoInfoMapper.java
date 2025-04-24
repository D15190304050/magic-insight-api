package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stark.magicinsight.domain.entities.UserVideoInfo;
import stark.magicinsight.dto.params.SearchVideoQueryParam;
import stark.magicinsight.dto.params.GetVideoInfosByUserIdQueryParam;
import stark.magicinsight.dto.params.GetVideoPlayInfoInPlaylistQueryParam;
import stark.magicinsight.dto.results.VideoPlayInfo;

import java.util.List;

@Mapper
public interface UserVideoInfoMapper
{
    int insert(UserVideoInfo userVideoInfo);
    int updateVideoInfoById(UserVideoInfo userVideoInfo);
    List<VideoPlayInfo> getVideoPlayInfosByUserId(GetVideoInfosByUserIdQueryParam getVideoInfosByUserIdQueryParam);
    long countVideoByUserId(long userId);
    UserVideoInfo getVideoBaseInfoById(long id);
    VideoPlayInfo getVideoPlayInfoById(@Param("videoId") long videoId, @Param("userId") long userId);
    long countVideoById(long id);
    long setVideoSummaryFileNameById(@Param("id") long id, @Param("summaryFileName") String summaryFileName,@Param("transcriptFileName") String transcriptFileName);
    String getVideoSummaryFileNameById(long id);
}
