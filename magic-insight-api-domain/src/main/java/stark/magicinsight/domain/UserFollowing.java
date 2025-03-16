package stark.magicinsight.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserFollowing extends DomainBase
{
    private long userId;
    private long followedUserId;
    private int followingStatus;
}
