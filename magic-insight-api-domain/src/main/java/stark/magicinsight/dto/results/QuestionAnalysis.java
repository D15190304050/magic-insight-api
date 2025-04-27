package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class QuestionAnalysis {
    private Integer coreQuestionCount;    // 核心问题数量
    private Integer evaluationCount;      // 评价次数
    private String analysisOfQuestioning; // 提问分析文本
    private String analysisOfEvaluation; // 评价分析文本
}