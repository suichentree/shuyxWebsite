package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxuser.dto.MenuDTO;
import com.shuyx.shuyxuser.dto.RoleMenuDTO;
import com.shuyx.shuyxuser.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

    public List<Integer> selectMenuIdsByRoleId(Integer roleId);
    public Integer batchInsertRoleMenu(List<RoleMenuDTO> list);
    public Integer deleteRoleMenuByRoleId(Integer roleId);

}
