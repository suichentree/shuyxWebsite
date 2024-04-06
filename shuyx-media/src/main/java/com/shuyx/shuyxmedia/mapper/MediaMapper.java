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
    public List<MediaDTO> findMediaAndGenre(MediaDTO one);
    List<MediaEntity> pageFindMediaAndGenre(@Param("genreIds")Integer[] genreIds,@Param("num")Integer num);

    List<MediaDTO> findMediaAndEpisodes(MediaDTO dto);
}
