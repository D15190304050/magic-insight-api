package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;

@Data
public class InteractionRecord
{
    private String question;
    private List<String> answers;
    private String feedback;
}