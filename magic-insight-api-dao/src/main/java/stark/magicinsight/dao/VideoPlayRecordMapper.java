package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import stark.magicinsight.domain.entities.VideoPlayRecord;

@Mapper
public interface VideoPlayRecordMapper
{
    int insert(VideoPlayRecord videoPlayRecord);
}
