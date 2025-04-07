package stark.magicinsight.service.doubao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.magicinsight.dto.results.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TranscriptAnalyzer
{
    public static final String ANALYSIS_FILE_PREFIX = "AnalyzerOfVideo";
    public static final String ANALYSIS_FILE_SUFFIX = ".json";

    public static final String SEND_TRANSCRIPT_PREFIX = "我这里有一份字幕，你接下来的回答都要严格根据这份字幕里的内容生成，下面是我的字幕\n";
    public static final String TRANSCRIPT_MODIFY = "这是我从视频中提取出的字幕文件，我需要你把修正后的纯字幕文件内容给我。要求如下：1、帮我修正一下识别后的错别字，如无法修正无需进行说明；2、删除因多人同时说话导致的语音识别到的重复内容；3、你返回给我的文字中不需要做其他多余操作（如不能进行备注与说明）";
    public static final String TRANSCRIPT_ANALYSIS = "请你基于我已初步修正的字幕文件，找出老师的核心提问,学生的回答，以及老师的反馈有哪些，把原文内容一一列举出来。注意question的type字段只是该集合元素：{\"是何问题\"，\"为何问题\"，\"如何问题\"，\"若何问题\"}，feedback的type字段只是该集合元素：{\"激励\"，\"否定\"，\"重复\"，\"针对肯定\"，\"简单肯定\"}";
    public static final String TRANSCRIPT_STRUCTURE =
            """
                    你的回答需要遵照以下输出格式：
                        输出嵌套层级的JSON格式，只需要输出我给出的字段
                        [
                                {
                                   "question": {
                                      "content":"",
                                      "type":""
                                   },
                                   "answers": [""],
                                   "feedback": {
                                      "content":"",
                                      "type":""
                                   }
                                },
                                {
                                   "question": {
                                      "content":"",
                                      "type":""
                                   },
                                   "answers": [""],
                                   "feedback": {
                                      "content":"",
                                      "type":""
                                   }
                                }
                            ]""";
    public static final String TRANSCRIPT_AICOURSEOVERVIEW = "请你基于我已初步修正的字幕文件，给出这节课的课程总览（150字左右），格式如：本节课主要讲了...的内容，介绍了...，讲解了...";
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
        SpeechRateAnalysis speechRateAnalysis = analyzeSpeedRate(transcript, modify, connection2);
        transcriptAnalysis.setSpeechRateAnalysis(speechRateAnalysis);

        //AI课程总览
        CourseAnalysis courseAnalysis = analyzeCourseOverview(speechRateAnalysis, interactionRecords, connection2);
        transcriptAnalysis.setCourseAnalysis(courseAnalysis);

        Integer totalMinutes = courseAnalysis.getTotalMinutes();
        //互动分析
        QuestionAnalysis questionAnalysis = analyzeQuestion(totalMinutes, interactionRecords, connection2);
        transcriptAnalysis.setQuestionAnalysis(questionAnalysis);

        //各个互动类型次数统计
        Map<String, Map<String, Integer>> interactionTypeCountMap = interactionTypeAnalyze(interactionRecords);
        transcriptAnalysis.setInteractionTypeCountMap(interactionTypeCountMap);
        return transcriptAnalysis;
    }

    /**
     * 语速分析
     * @param transcript
     * @param modify
     * @param connection2
     * @return
     */
    private static SpeechRateAnalysis analyzeSpeedRate(String transcript, String modify, DoubaoMultiRoundChatSession connection2)
    {
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
        speechRateAnalysis.setTotalSeconds(totalSeconds);
        int speed = wordCount / totalSeconds;
        speechRateAnalysis.setValue(speed);
        String analysisOfSpeechRate = connection2.runChat("当老师授课语速为" + speed + "分/秒时，你有什么建议，100字左右。");
        speechRateAnalysis.setAnalysisOfSpeechRate(analysisOfSpeechRate);

        return speechRateAnalysis;
    }

    /**
     * 互动分析
     * @param totalMinutes
     * @param interactionRecords
     * @param connection2
     * @return
     */
    private static QuestionAnalysis analyzeQuestion(Integer totalMinutes, List<InteractionRecord> interactionRecords, DoubaoMultiRoundChatSession connection2)
    {
        QuestionAnalysis questionAnalysis = new QuestionAnalysis();
        int coreQuestionCount = interactionRecords.size();
        questionAnalysis.setCoreQuestionCount(coreQuestionCount);
        int evaluationCount = 0;
        for (InteractionRecord record : interactionRecords)
        {
            if (record.getFeedback() != null) evaluationCount++;
        }
        questionAnalysis.setEvaluationCount(evaluationCount);
        String analysisOfQuestioning = connection2.runChat("当老师在总时长为" + totalMinutes + "分钟的课堂中，提问次数为" + coreQuestionCount + "次时，请你给出评价,50字左右");
        questionAnalysis.setAnalysisOfQuestioning(analysisOfQuestioning);
        String analysisOfEvaluation = connection2.runChat("当老师在总时长为" + totalMinutes + "分钟的课堂中，对于学生的回答，反馈次数为" + evaluationCount + "次时，请你给出评价,50字左右");
        questionAnalysis.setAnalysisOfEvaluation(analysisOfEvaluation);
        return questionAnalysis;
    }

    /**
     * 课程总览分析
     * @param speechRateAnalysis
     * @param interactionRecords
     * @param connection2
     * @return
     */
    public CourseAnalysis analyzeCourseOverview(SpeechRateAnalysis speechRateAnalysis,List<InteractionRecord> interactionRecords,DoubaoMultiRoundChatSession connection2)
    {

        CourseAnalysis courseAnalysis = new CourseAnalysis();
        String aiCourseOverview = connection2.runChat(TRANSCRIPT_AICOURSEOVERVIEW);
        courseAnalysis.setAiCourseOverview(aiCourseOverview);

        Integer totalMinutes = speechRateAnalysis.getTotalSeconds() / 60;
        courseAnalysis.setTotalMinutes(totalMinutes);

        Integer totalCountStudent = 0;
        for (InteractionRecord interactionRecord : interactionRecords)
        {
            List<String> answers = interactionRecord.getAnswers();
            for (String answer : answers)
            {
                totalCountStudent += answer.length();
            }
        }
        Integer proportionStudent = (totalCountStudent * 100) / (speechRateAnalysis.getWordCount());
        Integer proportionTeacher = 100 - proportionStudent;

        courseAnalysis.setProportionTeacher(proportionTeacher);
        courseAnalysis.setProportionStudent(proportionStudent);
        return courseAnalysis;
    }

    public static Map<String, Map<String, Integer>> interactionTypeAnalyze(List<InteractionRecord> records)
    {
        // 定义需要统计的类型（常量）
        String[] FEEDBACK_TYPES = {"激励", "否定", "重复", "针对肯定", "简单肯定"};
        String[] QUESTION_TYPES = {"是何问题", "为何问题", "如何问题", "若何问题"};

        // 初始化统计Map（所有类型预设为0）
        Map<String, Integer> feedbackStats = new HashMap<>();
        Map<String, Integer> questionStats = new HashMap<>();

        for (String type : FEEDBACK_TYPES)
        {
            feedbackStats.put(type, 0);
        }
        for (String type : QUESTION_TYPES)
        {
            questionStats.put(type, 0);
        }

        // 遍历记录并统计
        for (InteractionRecord record : records)
        {
            // 统计问题类型（跳过空值）
            String qType = record.getQuestion().getType();
            if (qType != null && !qType.isEmpty() && questionStats.containsKey(qType))
            {
                questionStats.put(qType, questionStats.get(qType) + 1);
            }

            // 统计反馈类型（跳过空值）
            String fType = record.getFeedback().getType();
            if (fType != null && !fType.isEmpty() && feedbackStats.containsKey(fType))
            {
                feedbackStats.put(fType, feedbackStats.get(fType) + 1);
            }
        }
        // 返回双层级结果
        Map<String, Map<String, Integer>> result = new HashMap<>();
        result.put("question", questionStats);
        result.put("feedback", feedbackStats);
        return result;
    }

    public static String getAnalysisFileName(long videoId)
    {
        return ANALYSIS_FILE_PREFIX + videoId + ANALYSIS_FILE_SUFFIX;
    }
}

