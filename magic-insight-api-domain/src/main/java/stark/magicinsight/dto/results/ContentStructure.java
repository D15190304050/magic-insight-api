package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;

@Data
public class ContentStructure
{
    private String title;
    private String content;
    private List<ContentStructure> children;
}
