package stark.magicinsight.dto.params;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class DeleteCommentsRequest
{
    @Min(value = 1, message = "Minimum video ID is 1.")
    private long id;
}
