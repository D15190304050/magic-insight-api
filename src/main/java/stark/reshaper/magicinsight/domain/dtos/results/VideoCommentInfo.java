package stark.reshaper.magicinsight.domain.dtos.results;

import lombok.Data;

import java.util.Date;

@Data
public class VideoCommentInfo
{
    private int id;
    private int userId;
    private String nickname;
    private String avatarUrl;
    private String content;
    private Date creationTime;
}