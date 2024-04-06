package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.dto.DictDTO;
import com.shuyx.shuyxuser.entity.DictEntity;

public interface DictService extends IService<DictEntity> {
    Object update(DictDTO dto);

    Object delete(Integer id);

    Object add(DictDTO dto);

    Object findBy(DictDTO dto);

    Object pagelist(DictDTO dto);
}
