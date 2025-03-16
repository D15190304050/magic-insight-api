package stark.magicinsight.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoUploadingState
{
    private String videoName;
    private long videoChunkCount;
    private String videoUploadingTaskId;
    private List<Long> finishedChunkIndexes;

}
