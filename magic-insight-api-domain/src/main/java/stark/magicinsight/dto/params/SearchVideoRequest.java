package stark.magicinsight.dto.params;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchVideoRequest extends PaginationRequestParam
{
    @NotBlank(message = "Keyword must not be null.")
    private String keyword;
}
