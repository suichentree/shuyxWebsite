package com.shuyx.shuyxmedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxmedia.dto.GenreDTO;
import com.shuyx.shuyxmedia.entity.GenreEntity;

public interface GenreService extends IService<GenreEntity> {
    Object addGenre(GenreEntity one);

    Object updateGenre(GenreEntity one);

    Object deleteGenre(Integer id);

    Object pagelist(GenreDTO dto);
}
