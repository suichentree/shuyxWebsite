package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxuser.dto.PositionDTO;
import com.shuyx.shuyxuser.entity.PositionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionMapper extends BaseMapper<PositionEntity> {
    public Object pagelist(PositionDTO dto);
}
