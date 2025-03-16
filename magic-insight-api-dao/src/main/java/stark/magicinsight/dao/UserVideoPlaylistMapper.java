package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stark.magicinsight.domain.UserVideoPlaylist;
import stark.magicinsight.dto.params.ModifyPlaylistInfoCommandParam;
import stark.magicinsight.dto.results.PlaylistInfo;
import stark.magicinsight.dto.results.PlaylistWithVideoCheck;

import java.util.List;

@Mapper
public interface UserVideoPlaylistMapper
{
    long countPlaylistByUserId(long userId);
    int insert(UserVideoPlaylist userVideoPlaylist);
    long deletePlaylistById(long id);
    UserVideoPlaylist getPlaylistById(long id);
    long countPlaylistById(long id);
    long countPlaylistByIdAndUserId(@Param("id") long id, @Param("userId") long userId);
    List<Long> getPlaylistIdsByUserId(long userId);
    int update(ModifyPlaylistInfoCommandParam userVideoPlaylist);
    List<PlaylistInfo> getPlaylistsByUserId(long userId);
    List<PlaylistWithVideoCheck> getPlaylistWithVideoChecks(@Param("userId") long userId, @Param("videoId") long videoId);
}
