package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaMapper extends BaseMapper<MediaEntity> {
    List<MediaDTO> findMediaAndTag(MediaDTO one);
    List<MediaEntity> pageFindMediaByTag(@Param("mediaType")String mediaType,@Param("tagIds")Integer[] tagIds,@Param("num")Integer num);

    List<MediaDTO> findMediaAndEpisodes(MediaDTO dto);
}
