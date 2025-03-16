package stark.magicinsight.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VideoSection extends DomainBase
{
    /**
     * Section of the video: 0 - None; 1 - Games; 2 - Action; 3 - Animation.
     */
    private String section;
}
