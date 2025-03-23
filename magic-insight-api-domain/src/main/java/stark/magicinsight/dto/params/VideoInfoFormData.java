package stark.magicinsight.dto.params;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class VideoInfoFormData
{
    @NotBlank(message = "Title of the video cannot be null.")
    private String title;

    @NotNull(message = "URL of the cover of the video cannot be null.")
    private String coverUrl;

    @Size(max = 1000, message = "You can type at most 1000 characters for introduction.")
    private String introduction;

    @Min(value = 1, message = "Minimum video ID is 1.")
    private long videoId;
}
