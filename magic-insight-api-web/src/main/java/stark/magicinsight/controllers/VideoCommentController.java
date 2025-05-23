package stark.magicinsight.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.PaginatedData;
import stark.dataworks.boot.web.ServiceResponse;
import stark.magicinsight.dto.params.AddCommentsRequest;
import stark.magicinsight.dto.params.DeleteCommentsRequest;
import stark.magicinsight.dto.params.GetCommentsByVideoIdRequest;
import stark.magicinsight.dto.results.VideoCommentInfo;
import stark.magicinsight.service.VideoCommentService;

@Slf4j
@RestController
@RequestMapping("/comment")
public class VideoCommentController
{
    @Autowired
    private VideoCommentService videoCommentService;

    @PostMapping("/add")
    public ServiceResponse<Long> addComment(@RequestBody AddCommentsRequest request)
    {
        return videoCommentService.addComment(request);
    }

    @GetMapping("/list")
    public ServiceResponse<PaginatedData<VideoCommentInfo>> getVideoCommentsById(@ModelAttribute GetCommentsByVideoIdRequest request)
    {
        return videoCommentService.getCommentsByVideoId(request);
    }

    @PostMapping("/delete")
    public ServiceResponse<Boolean> deleteComment(@RequestBody DeleteCommentsRequest request)
    {
        return videoCommentService.deleteComment(request);
    }
}
