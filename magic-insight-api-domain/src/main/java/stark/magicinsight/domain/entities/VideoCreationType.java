package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class VideoCreationType extends DomainBase
{
    /**
     * Video creation type: 0 - Original; 1 - Reprinting.
     */
    private String type;
}
