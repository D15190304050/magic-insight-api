package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.magicinsight.domain.entities.VideoCreationType;
import stark.magicinsight.domain.entities.VideoLabel;
import stark.magicinsight.domain.entities.VideoSection;

import java.util.List;

@Mapper
public interface VideoUploadingOptionMapper
{
    List<VideoCreationType> getAllVideoCreationTypes();
    List<VideoLabel> getAllVideoLabels();
    List<VideoSection> getAllVideoSections();
}
