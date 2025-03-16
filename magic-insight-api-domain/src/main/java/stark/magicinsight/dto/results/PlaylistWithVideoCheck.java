package stark.magicinsight.dto.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlaylistWithVideoCheck extends PlaylistInfo
{
    private long videoId;
    private boolean containsVideo;
}