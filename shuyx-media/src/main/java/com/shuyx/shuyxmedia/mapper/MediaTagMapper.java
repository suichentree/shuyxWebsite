package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.dto.MediaTagDTO;
import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.entity.MediaTagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaTagMapper extends BaseMapper<MediaTagEntity> {
     Integer batchInsertMediaTag(List<MediaTagDTO> dto);
     Integer batchDeleteMediaTag(List<MediaTagDTO> dto);
     Integer deleteMediaTagByMediaId(Integer mediaId);
}
