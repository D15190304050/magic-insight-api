package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.magicinsight.domain.VideoCreationType;
import stark.magicinsight.domain.VideoLabel;
import stark.magicinsight.domain.VideoSection;

import java.util.List;

@Mapper
public interface VideoUploadingOptionMapper
{
    List<VideoCreationType> getAllVideoCreationTypes();
    List<VideoLabel> getAllVideoLabels();
    List<VideoSection> getAllVideoSections();
}
