package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxuser.entity.RoleMenuEntity;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {
    public List<Integer> selectMenuIdsByRoleId(Integer roleId);
    public Integer batchInsertRoleMenu(List<RoleMenuEntity> list);
    public Integer deleteRoleMenuByRoleId(Integer roleId);
}
