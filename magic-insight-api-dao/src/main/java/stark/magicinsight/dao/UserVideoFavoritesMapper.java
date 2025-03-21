package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stark.magicinsight.domain.entities.UserVideoFavorites;
import stark.magicinsight.dto.params.GetUserFavoritesByPlaylistIdParam;

import java.util.List;

@Mapper
public interface UserVideoFavoritesMapper
{
    int insert(UserVideoFavorites userVideoFavorites);
    int delete(@Param("videoId") long videoId, @Param("playlistId") long playlistId, @Param("userId") long userId);
    UserVideoFavorites getVideoByVideoIdAndPlaylistId(@Param("videoId") long videoId, @Param("playlistId") long playlistId, @Param("userId") long userId);
    long countVideoInPlaylist(@Param("videoId") long videoId, @Param("playlistId") long playlistId, @Param("userId") long userId);
    List<UserVideoFavorites> getFavoritesByPlaylistId(GetUserFavoritesByPlaylistIdParam userFavoritesByPlaylistIdParam);
    long countVideoByPlaylistId(@Param("playlistId") long playlistId, @Param("userId") long userId);
    long deleteFavoritesInPlaylist(long playlistId);
    long deleteFavoritesByUserAndVideoId(@Param("userId") long userId, @Param("videoId") long videoId);
    long deleteFavoritesNotInRange(@Param("userId") long userId, @Param("videoId") long videoId, @Param("playlistIds") List<Long> playlistIds);
    long addFavoritesInRange(@Param("userId") long userId, @Param("videoId") long videoId, @Param("playlistIds") List<Long> playlistIds);
    long countVideosInPlaylist(long playlistId);
}
