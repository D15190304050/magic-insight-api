package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class SpeechRateAnalysis {
    private Integer value;      // 语速值
    private String unit = "（字/秒）";     // 单位（字/秒）
    private Integer wordCount; // 总字数
    private String analysisOfSpeechRate; // 语速分析文本
}