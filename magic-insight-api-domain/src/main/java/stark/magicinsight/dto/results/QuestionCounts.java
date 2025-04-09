package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class QuestionCounts
{
    //是何、为何、如何、若何 四类问题
    private Integer what;
    private Integer why;
    private Integer how;
    private Integer whatIf;
}
