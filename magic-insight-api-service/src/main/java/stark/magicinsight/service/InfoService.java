package stark.magicinsight.service;

import org.springframework.stereotype.Service;
import stark.magicinsight.dto.results.CourseAnalysis;
import stark.magicinsight.dto.results.InteractionRecord;
import stark.magicinsight.dto.results.SpeechRateAnalysis;
import stark.magicinsight.dto.results.TranscriptAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoService
{
    public CourseAnalysis courseOverviewSummarize(TranscriptAnalysis transcriptAnalysis)
    {
        SpeechRateAnalysis speechRateAnalysis = transcriptAnalysis.getSpeechRateAnalysis();
        Integer totalMinutes = speechRateAnalysis.getTotalSeconds() / 60;
        CourseAnalysis courseAnalysis = transcriptAnalysis.getCourseAnalysis();
        courseAnalysis.setTotalMinutes(totalMinutes);

        Integer totalCountStudent = 0;
        List<InteractionRecord> interactionRecords = transcriptAnalysis.getInteractionRecords();
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
}
