package stark.magicinsight.service.doubao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.magicinsight.dto.results.InteractionRecord;
import stark.magicinsight.dto.results.TranscriptAnalysis;

import java.util.List;

@Slf4j
@Service
public class DoubaoAnalyzer
{
    public static final String SUMMARY_FILE_PREFIX = "AnalyzerOfVideo";
    public static final String SUMMARY_FILE_SUFFIX = ".json";

    public static final String SEND_TRANSCRIPT_PREFIX = "我这里有一份字幕，你接下来的回答都要严格根据这份字幕里的内容生成，下面是我的字幕\n";
    public static final String TRANSCRIPT_MODIFY = "这是我从视频中提取出的字幕文件，我需要你把修正后的纯字幕文件内容给我。要求如下：1、帮我修正一下识别后的错别字，如无法修正无需进行说明；2、删除因多人同时说话导致的语音识别到的重复内容；3、你返回给我的文字中不需要做其他多余操作（如不能进行备注与说明，不能丢失时间信息）";
    public static final String TRANSCRIPT_ANALYSIS = "请你基于我已初步修正的字幕文件，找出老师的核心提问,学生的回答，以及老师的反馈有哪些，把原文内容一一列举出来。";
    public static final String TRANSCRIPT_STRUCTURE =
            """
                    你的回答需要遵照以下输出格式：
                        输出嵌套层级的JSON格式，只需要输出我给出的字段
                        {
                            "interactionRecords": [
                                {
                                    "question": "",
                                    "answers": [""],
                                    "feedback": ""
                                },
                                {
                                    "question": "",
                                    "answers": [""],
                                    "feedback": ""
                                }
                            ]
                        }""";

    @Autowired
    private DoubaoMultiRoundChatSessionFactory doubaoMultiRoundChatSessionFactory;

    public TranscriptAnalysis analyze(String transcript)
    {
        DoubaoMultiRoundChatSession connection = doubaoMultiRoundChatSessionFactory.build();
        connection.runChat(SEND_TRANSCRIPT_PREFIX + transcript);
        String modify = connection.runChat(TRANSCRIPT_MODIFY);

        DoubaoMultiRoundChatSession connection2 = doubaoMultiRoundChatSessionFactory.build();
        String analysisJsonText = connection2.runChat(TRANSCRIPT_ANALYSIS + TRANSCRIPT_STRUCTURE + modify);

        List<InteractionRecord> interactionRecords = JsonSerializer.deserializeList(analysisJsonText, InteractionRecord.class);

        TranscriptAnalysis transcriptAnalysis = new TranscriptAnalysis();
        transcriptAnalysis.setInteractionRecords(interactionRecords);
        return transcriptAnalysis;
    }

    public static String getSummaryFileName(long videoId)
    {
        return SUMMARY_FILE_PREFIX + videoId + SUMMARY_FILE_SUFFIX;
    }
}

