package stark.magicinsight.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VideoLabel extends DomainBase
{
    /**
     * The label.
     */
    private String label;
}
