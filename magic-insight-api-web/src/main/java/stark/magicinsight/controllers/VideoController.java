package stark.magicinsight.controllers;

import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stark.dataworks.boot.web.ServiceResponse;
import stark.magicinsight.dto.params.*;
import stark.magicinsight.dto.results.TranscriptAnalysis;
import stark.magicinsight.dto.results.TranscriptSummary;
import stark.magicinsight.dto.results.VideoPlayInfo;
import stark.magicinsight.service.ImageService;
import stark.magicinsight.service.VideoService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/video")
public class VideoController
{
    @Autowired
    private VideoService videoService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/generate-task")
    public ServiceResponse<String> generateNewVideoUploadingTask(@ModelAttribute NewVideoUploadingTaskRequest request)
    {
        return videoService.generateNewVideoUploadingTask(request);
    }

    @PostMapping("/upload-chunk")
    public ServiceResponse<Boolean> uploadVideoChunk(@ModelAttribute VideoChunkUploadingRequest request, @RequestPart("videoChunk") MultipartFile videoChunk) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        request.setVideoChunk(videoChunk);
        return videoService.uploadVideoChunk(request);
    }

    @PostMapping("/compose-chunks")
    public ServiceResponse<Long> composeVideoChunks(@RequestBody ComposeVideoChunksRequest request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ExecutionException, InterruptedException
    {
        return videoService.composeVideoChunks(request);
    }

    @PostMapping("/upload-cover")
    public ServiceResponse<String> uploadVideoCover(@RequestParam("coverFile") MultipartFile coverFile)
    {
        return imageService.uploadVideoCover(coverFile);
    }

    @GetMapping("/cover/{coverFileName}")
    public void getVideoCover(@PathVariable("coverFileName") String coverFileName, HttpServletResponse response)
    {
        imageService.getImage(coverFileName, response);
    }

    @PostMapping("/set-info")
    public ServiceResponse<Boolean> setVideoInfo(@RequestBody VideoInfoFormData request) throws ExecutionException, InterruptedException
    {
        return videoService.setVideoInfo(request);
    }

    @GetMapping("/list")
    public ServiceResponse<List<VideoPlayInfo>> getVideoInfoOfCurrentUser(@ModelAttribute PaginationRequestParam paginationRequestParam)
    {
        return videoService.getVideoInfoOfCurrentUser(paginationRequestParam);
    }

    @GetMapping("/count")
    public ServiceResponse<Long> countVideoByUserId()
    {
        return videoService.countVideoByUserId();
    }

    @PutMapping("/update")
    public ServiceResponse<Boolean> updateVideoInfo(@RequestBody VideoInfoFormData request)
    {
        return videoService.updateVideoInfo(request);
    }

    @GetMapping("/info")
    public ServiceResponse<VideoInfoFormData> getVideoInfoFormDataById(@RequestParam("videoId") long videoId)
    {
        return videoService.getVideoInfoFormDataById(videoId);
    }

    @GetMapping("/play")
    public ServiceResponse<VideoPlayInfo> getVideoInfoById(@RequestParam("videoId") long videoId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        return videoService.getVideoPlayInfoById(videoId);
    }

    @GetMapping("/summary")
    public ServiceResponse<TranscriptSummary> getSummaryOfVideo(@RequestParam("videoId") long videoId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        return videoService.getSummaryOfVideo(videoId);
    }

    @GetMapping("/analysis")
    public ServiceResponse<TranscriptAnalysis> getAnalysisOfVideo(@RequestParam("videoId") long videoId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        return videoService.getAnalysisOfVideo(videoId);
    }
}
