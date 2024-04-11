package com.shuyx.shuyxmedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.dto.MediaTagDTO;
import com.shuyx.shuyxmedia.entity.MediaTagEntity;

import java.util.List;

public interface MediaTagService extends IService<MediaTagEntity> {
    Object delete(Integer id);

    Object findBy(MediaTagDTO dto);

    Object saveOrUpdateBatch(MediaDTO dto);
}
