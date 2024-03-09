package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.dto.GenreDTO;
import com.shuyx.shuyxmedia.entity.GenreEntity;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreMapper extends BaseMapper<GenreEntity> {
    public List<GenreEntity> findGenreByMediaId(Integer mediaId);
}
