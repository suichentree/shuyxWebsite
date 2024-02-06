package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.mapper.RoleMapper;
import com.shuyx.shuyxuser.mapper.UserMapper;
import com.shuyx.shuyxuser.mapper.UserRoleMapper;
import com.shuyx.shuyxuser.service.MenuService;
import com.shuyx.shuyxuser.service.UserService;
import com.shuyx.shuyxuser.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 注册
     * @param userEntity
     * @return
     */
    @Override
    public Object register(UserEntity userEntity) {
        //先查询该用户名称是否存在
        String userName = userEntity.getUserName();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        UserEntity one = userMapper.selectOne(queryWrapper);
        //若查询用户不为空
        if(one != null){
            log.info("该用户已经存在，无法注册");
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_IS_INVALID);
        }
        //新增用户
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setStatus(0);
        int insert = userMapper.insert(userEntity);
        if(insert == 0){
            log.info("用户注册失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.USER_REGISTER_IS_FAILED);
        }
        return ReturnUtil.success(userEntity);
    }

    /**
     * 用户登录
     * 若用户登录成功，则生成token,并返回
     * @param userName
     * @param passWord
     * @return
     */
    @Override
    public Object login(String userName, String passWord) {
        //根据用户名查询用户
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        //若查询用户为空
        if(userEntity ==null){
            log.info("该用户不存在，无法登录");
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_IS_INVALID);
        }
        //若用户状态非0（0正常1禁用）
        if(userEntity.getStatus() != 0 ){
            log.info("该用户状态不为0");
            return ReturnUtil.fail(ResultCodeEnum.USER_STATUS_IS_INVALID);
        }
        //密码比对
        boolean isPassword = StringUtils.equals(passWord, userEntity.getPassWord());
        if(!isPassword){
            log.info("登录密码错误");
            return ReturnUtil.fail(ResultCodeEnum.USER_PASSWORD_IS_ERROR);
        }
        //密码正确，登录成功，生成token
        Map map = new HashMap<String, String>();
        map.put("userId",userEntity.getUserId().toString());
        map.put("userName",userEntity.getUserName());
        String token = JWTUtil.createToken(map);

        //将用户登录信息缓存到redis中,3小时过期
        redisUtil.hPutMap(RedisKeyConstant.USER_LOGIN_INFO+userEntity.getUserId(),map);
        redisUtil.expire(RedisKeyConstant.USER_LOGIN_INFO+userEntity.getUserId(),3, TimeUnit.HOURS);

        //登录成功,获取该用户的可访问菜单路由信息并存储
//        Object userMenuInfo = menuService.selectUserMenuTreeInfo(userEntity.getUserId());
        return ReturnUtil.success(token);
    }

    @Override
    public Object logout(Integer userId, String username, String token) {
        Boolean aBoolean = JWTUtil.checkToken(token);
        return null;
    }

    @Override
    public Object userInfo(String token) {
        //取出token中的信息
        Map<String, Object> tokenInfo = JWTUtil.parseToken(token);
        Integer userId = Integer.parseInt(tokenInfo.get("userId").toString());
        String userName = tokenInfo.get("userName").toString();
        //查询用户
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("user_name",userName);
        UserEntity one = userMapper.selectOne(queryWrapper);
        if(one == null){
            log.info("token中的用户信息错误，该用户在数据库中不存在");
            return ReturnUtil.fail(ResultCodeEnum.USER_SELECT_IS_NULL);
        }
        one.setPassWord("******");
        return ReturnUtil.success(one);
    }

    /**
     * 用户分页查询
     * @param userDTO
     * @return
     */
    @Override
    public Object pagelist(UserDTO userDTO) {
        //使用PageHelper分页插件来进行分页操作
        PageHelper.startPage(userDTO.getPageNum(),userDTO.getPageSize());
        List<UserDTO> userOrgList = userMapper.selectUserOrgList(userDTO);
        PageInfo pageInfo = new PageInfo<>(userOrgList);
        return ReturnUtil.success(pageInfo);
    }

    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    @Override
    public Object addUser(UserEntity userEntity) {
        //根据名称查询该用户是否存在
        String userName = userEntity.getUserName();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        UserEntity one = userMapper.selectOne(queryWrapper);
        //若查询用户不为空
        if(one != null){
            log.info("该用户已经存在，无法新增");
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_IS_INVALID);
        }
        //新增用户
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setStatus(0);
        int insert = userMapper.insert(userEntity);
        if(insert == 0){
            log.info("用户添加失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public Object deleteUser(Integer id) {
        int update = userMapper.deleteById(id);
        if(update == 0){
            log.info("用户删除失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public Object updateUser(UserEntity user) {
        int update = userMapper.updateById(user);
        if(update == 0){
            log.info("用户更新失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public Object selectUserById(Integer id) {
        UserDTO dto = new UserDTO();
        dto.setUserId(id);
        List<UserDTO> userOrgList = userMapper.selectUserOrgList(dto);
        if(userOrgList.size() == 0){
            log.info("查询用户信息为空。");
            return ReturnUtil.fail(ResultCodeEnum.USER_SELECT_IS_NULL);
        }
        //返回第一条数据
        return ReturnUtil.success(userOrgList.get(0));
    }

    /**
     * 查询拥有该角色id的用户
     * @param dto
     * @return
     */
    @Override
    public Object selectUserListByRoleId(RoleDTO dto) {
        //先查询用户角色表
        RoleEntity one = roleMapper.selectById(dto.getRoleId());
        if(one == null){
            log.info("该角色id无效");
            return ReturnUtil.fail(ResultCodeEnum.ROLE_ID_IS_INVALID);
        }
        //查询该角色下的用户列表分页信息
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<UserEntity> list = userMapper.selectUserListByRoleId(dto.getRoleId());
        PageInfo pageInfo = new PageInfo<>(list);
        return ReturnUtil.success(pageInfo);
    }

    /**
     * 查询不拥有该角色id的用户
     * @param dto
     * @return
     */
    @Override
    public Object selectUserListByNoRoleId(UserRoleDTO dto) {
        //先查询用户角色表
        RoleEntity one = roleMapper.selectById(dto.getRoleId());
        if(one == null){
            log.info("该角色id无效");
            return ReturnUtil.fail(ResultCodeEnum.ROLE_ID_IS_INVALID);
        }
        //查询不属于该角色下的用户列表分页信息
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<UserEntity> list = userMapper.selectUserListByNoRoleId(dto);
        PageInfo pageInfo = new PageInfo<>(list);
        return ReturnUtil.success(pageInfo);
    }

    /**
     * 向用户角色表添加数据
     * @param dto
     * @return
     */
    @Override
    public Object addUserRole(List<UserRoleDTO> dto) {
        //将角色与用户绑定
        Integer i = userRoleMapper.batchInsertUserRole(dto);
        if(i == 0){
            log.info("将角色与用户绑定，业务失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 在用户角色表删除数据
     * @param dto
     * @return
     */
    @Override
    public Object deleteUserRole(List<UserRoleDTO> dto) {
        //解除角色与用户绑定
        Integer i = userRoleMapper.batchDeleteUserRole(dto);
        if(i == 0){
            log.info("解除角色与用户绑定，业务失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

}
