package stark.magicinsight.service.doubao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.magicinsight.dto.results.InteractionRecord;
import stark.magicinsight.dto.results.QuestionAnalysis;
import stark.magicinsight.dto.results.SpeechRateAnalysis;
import stark.magicinsight.dto.results.TranscriptAnalysis;

import java.util.List;

@Slf4j
@Service
public class DoubaoAnalyzer
{
    public static final String ANALYSIS_FILE_PREFIX = "AnalyzerOfVideo";
    public static final String ANALYSIS_FILE_SUFFIX = ".json";

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

        //语速分析
        SpeechRateAnalysis speechRateAnalysis = new SpeechRateAnalysis();
        int wordCount = modify.replaceAll("\\s", "").length();//还没减去学生发言的字数
        speechRateAnalysis.setWordCount(wordCount);
        //这里要用带有时间信息的字幕文件把总时长提取出来,拿到的格式是时分秒毫秒00:37:48,672
        String[] lines = transcript.split("\n");
        String totalTime = lines[lines.length - 2].split(" ")[2];
        String[] hhmmss = totalTime.split(",")[0].split(":");
        int totalSeconds = Integer.parseInt(hhmmss[0]) * 3600
                + Integer.parseInt(hhmmss[1]) * 60
                + Integer.parseInt(hhmmss[2]);
        int speed = wordCount / totalSeconds;
        speechRateAnalysis.setValue(speed);
        String analysisOfSpeechRate = connection2.runChat("当老师授课语速为" + speed + "分/秒时，你有什么建议，100字左右。");
        speechRateAnalysis.setAnalysisOfSpeechRate(analysisOfSpeechRate);
        transcriptAnalysis.setSpeechRateAnalysis(speechRateAnalysis);

        //互动分析
        QuestionAnalysis questionAnalysis = new QuestionAnalysis();
        int coreQuestionCount = interactionRecords.size();
        questionAnalysis.setCoreQuestionCount(coreQuestionCount);
        transcriptAnalysis.setQuestionAnalysis(questionAnalysis);
        int evaluationCount = 0;
        for (InteractionRecord record : interactionRecords)
        {
            if (record.getFeedback() != null && !record.getFeedback().isEmpty()) evaluationCount++;
        }
        questionAnalysis.setEvaluationCount(evaluationCount);
        String analysisOfQuestioning = connection2.runChat("当老师在总时长为"+totalSeconds+"秒的课堂中，提问次数为"+"coreQuestionCount"+"次时，请你给出评价,50字左右");
        questionAnalysis.setAnalysisOfQuestioning(analysisOfQuestioning);
        String analysisOfEvaluation =  connection2.runChat("当老师在总时长为"+totalSeconds+"秒的课堂中，对于学生的回答，反馈次数为"+"evaluationCount"+"次时，请你给出评价,50字左右");
        questionAnalysis.setAnalysisOfEvaluation(analysisOfEvaluation);
        transcriptAnalysis.setQuestionAnalysis(questionAnalysis);
        return transcriptAnalysis;
    }

    public static String getAnalysisFileName(long videoId)
    {
        return ANALYSIS_FILE_PREFIX + videoId + ANALYSIS_FILE_SUFFIX;
    }
}

