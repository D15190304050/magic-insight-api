package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;

@Data
public class TranscriptAnalysis {
    private SpeechRateAnalysis speechRateAnalysis;
    private QuestionAnalysis questionAnalysis;
    private List<InteractionRecord> interactionRecords;
}