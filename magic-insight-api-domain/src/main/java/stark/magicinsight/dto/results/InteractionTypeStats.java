package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class InteractionTypeStats
{
    private QuestionCounts questionCounts;
    private FeedbackCounts feedbackCounts;
}
