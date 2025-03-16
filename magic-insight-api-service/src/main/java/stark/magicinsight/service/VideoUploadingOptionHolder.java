package stark.magicinsight.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;
import stark.magicinsight.dao.VideoUploadingOptionMapper;
import stark.magicinsight.domain.VideoCreationType;
import stark.magicinsight.domain.VideoLabel;
import stark.magicinsight.domain.VideoSection;
import stark.magicinsight.dto.results.DropDownOption;
import stark.magicinsight.dto.results.VideoUploadingOption;
import stark.magicinsight.service.handlers.RecordToOptionHandler;

import java.util.List;

@Slf4j
@Service
@LogArgumentsAndResponse
public class VideoUploadingOptionHolder
{
    @Autowired
    private VideoUploadingOptionMapper videoUploadingOptionMapper;

    private final VideoUploadingOption videoUploadingOption;

    public VideoUploadingOptionHolder()
    {
        videoUploadingOption = new VideoUploadingOption();
    }

    public void setVideoUploadingOptions() throws IllegalAccessException
    {
        List<VideoCreationType> allVideoCreationTypes = videoUploadingOptionMapper.getAllVideoCreationTypes();
        List<VideoLabel> allVideoLabels = videoUploadingOptionMapper.getAllVideoLabels();
        List<VideoSection> allVideoSections = videoUploadingOptionMapper.getAllVideoSections();

        List<DropDownOption<Long>> creationTypeOptions = RecordToOptionHandler.convertRecordsToDropDownOption(allVideoCreationTypes, "type", "id");
        List<DropDownOption<Long>> videoLabelOptions = RecordToOptionHandler.convertRecordsToDropDownOption(allVideoLabels, "label", "id");
        List<DropDownOption<Long>> videoSectionOptions = RecordToOptionHandler.convertRecordsToDropDownOption(allVideoSections, "section", "id");

        videoUploadingOption.setCreationTypeOptions(creationTypeOptions);
        videoUploadingOption.setVideoLabelOptions(videoLabelOptions);
        videoUploadingOption.setVideoSectionOptions(videoSectionOptions);
    }

    public ServiceResponse<VideoUploadingOption> getVideoUploadingOptions()
    {
        return ServiceResponse.buildSuccessResponse(videoUploadingOption);
    }
}
