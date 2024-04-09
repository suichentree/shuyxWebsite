package com.shuyx.shuyxmedia.mapper;

import com.shuyx.shuyxmedia.dto.MediaTagDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaTagMapper {
    public Integer batchInsertMediaTag(List<MediaTagDTO> dto);
    public Integer batchDeleteMediaTag(List<MediaTagDTO> dto);
    public Integer deleteMediaTagByMediaId(Integer mediaId);
}
