package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<TagEntity> {
    List<TagEntity> findTagByMediaId(Integer mediaId);
}
