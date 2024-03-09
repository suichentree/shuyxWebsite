package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.dto.MediaGenreDTO;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaGenreMapper{
    public Integer batchInsertMediaGenre(List<MediaGenreDTO> dto);
    public Integer batchDeleteMediaGenre(List<MediaGenreDTO> dto);
    public Integer deleteMediaGenreByMediaId(Integer mediaId);
}
