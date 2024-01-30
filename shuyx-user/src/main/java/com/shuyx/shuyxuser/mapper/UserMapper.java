package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 分页查询用户信息
     * @param dto
     * @return
     */
    public List<UserDTO> selectUserOrgList(UserDTO dto);
}
