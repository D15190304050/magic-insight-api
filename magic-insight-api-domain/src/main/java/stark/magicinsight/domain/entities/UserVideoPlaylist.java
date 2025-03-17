package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

/**
 * Video playlists of users.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class UserVideoPlaylist extends DomainBase
{
    /**
     * ID of the user who owns the playlist.
     */
    private long userId;

    /**
     * Name of the playlist.
     */
    private String name;

    /**
     * Description of the playlist.
     */
    private String description;
}
