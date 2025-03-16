package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class PlaylistInfo
{
    private long id;
    private long userId;
    private String name;
    private String description;
    private long videoCount;
}
