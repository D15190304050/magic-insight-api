package stark.magicinsight.service.kafka;

import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.autoconfig.minio.EasyMinio;
import stark.magicinsight.dao.UserVideoInfoMapper;
import stark.magicinsight.dto.params.VideoSummaryEndMessage;
import stark.magicinsight.dto.results.TranscriptAnalysis;
import stark.magicinsight.service.doubao.TranscriptAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class ConsumerService
{
    public static final long SUMMARY_THRESHOLD = 1000;

    @Value("${dataworks.easy-minio.bucket-name-video-subtitles}")
    private String bucketNameVideoSubtitles;

    @Value("${dataworks.easy-minio.bucket-name-summaries}")
    private String bucketNameSummaries;

    @Autowired
    private EasyMinio easyMinio;

    @Autowired
    private UserVideoInfoMapper userVideoInfoMapper;
    @Autowired
    private TranscriptAnalyzer transcriptAnalyzer;

    @KafkaListener(topics = {"${spring.kafka.consumer.topic-summary-video-end}"},
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory",
            properties = {"${spring.kafka.consumer.auto-offset-reset}"})
    public void handleMessage(ConsumerRecord<String, String> record, Acknowledgment ack)
    {
        String message = record.value();
        log.info("Received data, topic = {}, value = {}", record.topic(), message);

        try
        {
            VideoSummaryEndMessage summaryEndMessage = JsonSerializer.deserialize(message, VideoSummaryEndMessage.class);
            handleMessage(summaryEndMessage);
        }
        catch (Exception e)
        {
            log.error("Error consuming message, value = {}", message, e);
        }
        finally
        {
            // Submit offset manually.
            ack.acknowledge();
            log.info("Done consuming message, value = {}", message);
        }
    }

    public TranscriptAnalysis handleMessage(VideoSummaryEndMessage summaryEndMessage) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        long videoId = summaryEndMessage.getVideoId();
        String subtitleObjectName = summaryEndMessage.getSubtitleObjectName();

        String transcript = getTranscript(subtitleObjectName);
        TranscriptAnalysis analysis;

        // We don't generate summary for transcript with length less than SUMMARY_THRESHOLD.
        if (StringUtils.hasText(transcript) && transcript.length() > SUMMARY_THRESHOLD)
        {
            analysis = transcriptAnalyzer.analyze(transcript);
        }
        else
        {
            analysis = new TranscriptAnalysis();
        }
        log.info("videoId = "+videoId);
        saveAnalysis(videoId, analysis);


        return analysis;
    }

    private String getTranscript(String subtitleObjectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        InputStream objectInputStream = easyMinio.getObjectInputStream(bucketNameVideoSubtitles, subtitleObjectName);
        byte[] byteContent = objectInputStream.readAllBytes();
        return new String(byteContent, StandardCharsets.UTF_8);
    }

    private void saveAnalysis(long videoId, TranscriptAnalysis transcriptAnalysis) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String analysisFileName = saveAnalysisToMinio(videoId, transcriptAnalysis);
        saveAnalysisFileNameToDb(videoId, analysisFileName);
    }

    private String saveAnalysisToMinio(long videoId, TranscriptAnalysis transcriptAnalysis) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String analysisFileName = TranscriptAnalyzer.getAnalysisFileName(videoId);
        easyMinio.putObject(bucketNameSummaries, analysisFileName, transcriptAnalysis);
        return analysisFileName;
    }

    private void saveAnalysisFileNameToDb(long videoId, String summaryFileName)
    {
        userVideoInfoMapper.setVideoSummaryFileNameById(videoId, summaryFileName);
    }
}

