package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.dto.PositionDTO;
import com.shuyx.shuyxuser.entity.PositionEntity;

public interface PositionService extends IService<PositionEntity> {
    public Object pagelist(PositionDTO positionDTO);
    public Object addPosition(PositionEntity entity);
    public Object deletePosition(Integer id);
    public Object updatePosition(PositionEntity entity);
    public Object selectPositionById(Integer id);
}
