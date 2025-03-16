package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class LoginStateToken
{
    private String accessToken;
    private String refreshToken;
    private String expirationInSeconds;
}
