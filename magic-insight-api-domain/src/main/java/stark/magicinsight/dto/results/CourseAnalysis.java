package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class CourseAnalysis
{
    private Integer totalMinutes;
    private Integer proportionTeacher;
    private Integer proportionStudent;
    private String aiCourseOverview;
}
