package stark.magicinsight.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import stark.coderaider.fluentschema.commons.annotations.NotMapped;

@EqualsAndHashCode(callSuper = true)
@Data
@NotMapped
public class VideoLabel extends DomainBase
{
    /**
     * The label.
     */
    private String label;
}
