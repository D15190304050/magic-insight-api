package stark.magicinsight.controllers;

import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;
import stark.magicinsight.dto.params.VideoSummaryEndMessage;
import stark.magicinsight.dto.results.TranscriptAnalysis;
import stark.magicinsight.service.kafka.ConsumerService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController
{
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/hi")
    public String sayHi()
    {
        return "hi";
    }

    @PostMapping("/analyze")
    public ServiceResponse<TranscriptAnalysis> testTranscriptAnalysis(@RequestBody VideoSummaryEndMessage videoSummaryEndMessage) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        TranscriptAnalysis transcriptAnalysis = consumerService.handleMessage(videoSummaryEndMessage);
        return ServiceResponse.buildSuccessResponse(transcriptAnalysis);
    }
}
