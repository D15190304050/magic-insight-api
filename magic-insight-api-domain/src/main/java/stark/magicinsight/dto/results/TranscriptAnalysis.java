package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TranscriptAnalysis {
    private SpeechRateAnalysis speechRateAnalysis;
    private QuestionAnalysis questionAnalysis;
    private List<InteractionRecord> interactionRecords;
    private CourseAnalysis courseAnalysis;
    private Map<String, Map<String, Integer>> interactionTypeCountMap;
}