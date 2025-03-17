package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class VideoSection extends DomainBase
{
    /**
     * Section of the video: 0 - None; 1 - Games; 2 - Action; 3 - Animation.
     */
    private String section;
}
