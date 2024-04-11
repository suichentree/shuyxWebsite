package com.shuyx.shuyxmedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxmedia.dto.TagDTO;
import com.shuyx.shuyxmedia.entity.TagEntity;

public interface TagService extends IService<TagEntity> {
    Object addTag(TagEntity one);

    Object updateTag(TagEntity one);

    Object deleteTag(Integer id);

    Object pagelist(TagDTO dto);

    Object findBy(TagDTO dto);

    Object findByMediaId(Integer mediaId);
}
