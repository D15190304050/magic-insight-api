package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class FeedbackCounts
{
    //激励、否定、重复、针对肯定、简单肯定
    private Integer motivate;
    private Integer negative;
    private Integer repeat;
    private Integer targetedAffirmative;
    private Integer simpleAffirmative;
}
