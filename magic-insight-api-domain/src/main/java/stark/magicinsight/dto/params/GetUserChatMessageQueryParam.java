package stark.magicinsight.dto.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetUserChatMessageQueryParam extends PaginationQueryParam
{
    private long sessionId;
}
