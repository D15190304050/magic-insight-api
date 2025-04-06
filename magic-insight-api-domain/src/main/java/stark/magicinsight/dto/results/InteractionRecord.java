package stark.magicinsight.dto.results;

import lombok.Data;

import java.util.List;

@Data
public class InteractionRecord
{
    private Question question;//判断类型，是何问题  为何问题  如何问题  若何问题
    private List<String> answers;
    private Feedback feedback;//判断类型，激励  否定  重复  针对肯定  简单肯定
}