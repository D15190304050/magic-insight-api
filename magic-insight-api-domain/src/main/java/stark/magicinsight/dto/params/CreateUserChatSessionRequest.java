package stark.magicinsight.dto.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserChatSessionRequest
{
    @NotBlank(message = "Recipient name is required.")
    private String recipientName;
}
