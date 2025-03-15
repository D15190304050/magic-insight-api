package stark.reshaper.magicinsight.domain.dtos.results;

import lombok.Data;

@Data
public class LoginStateToken
{
    private String accessToken;
    private String refreshToken;
    private String expirationInSeconds;
}
