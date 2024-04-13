package com.shuyx.shuyxmedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.entity.MediaEntity;

public interface MediaService extends IService<MediaEntity> {
    Object addMedia(MediaDTO dto);

    Object updateMedia(MediaDTO one);

    Object deleteMedia(Integer id);

    Object pagelist(MediaDTO dto);

    Object findBy(MediaDTO dto);
    Object findMediaAndTag(MediaDTO dto);

    Object pageFindMediaByTag(MediaDTO dto);

    Object findMediaAndEpisodes(MediaDTO dto);

    Object updateMedia2(MediaDTO one);
}
