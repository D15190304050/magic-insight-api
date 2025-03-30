package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;

@Data
public class TranscriptSummary
{
    private boolean canSummarize;
    private String summary;
    private List<ContentStructure> contentStructures;
    private List<String> labels;
}
