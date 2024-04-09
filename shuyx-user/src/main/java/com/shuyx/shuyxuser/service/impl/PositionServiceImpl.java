package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.PositionDTO;
import com.shuyx.shuyxuser.entity.PositionEntity;
import com.shuyx.shuyxuser.mapper.PositionMapper;
import com.shuyx.shuyxuser.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionEntity> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public Object pagelist(PositionDTO positionDTO) {
        QueryWrapper<PositionEntity> queryWrapper = new QueryWrapper<>();
        //名称查询
        if(StringUtils.isNotBlank(positionDTO.getPositionName())){
            queryWrapper.like("position_name",positionDTO.getPositionName());
        }
        //状态查询
        if(positionDTO.getStatus()!=null){
            queryWrapper.eq("status",positionDTO.getStatus());
        }
        //分页查询
        Page<PositionEntity> page = new Page<>();
        page.setCurrent(positionDTO.getPageNum());
        page.setSize(positionDTO.getPageSize());
        Page<PositionEntity> pages = positionMapper.selectPage(page, queryWrapper);
        if(pages == null){
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_SELECT_FAILED);
        }
        return ReturnUtil.success(pages);
    }

    @Override
    public Object addPosition(PositionEntity entity) {
        //先查询该名称是否存在
        QueryWrapper<PositionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position_name",entity.getPositionName());
        PositionEntity one = positionMapper.selectOne(queryWrapper);
        //若查询不为空
        if(one != null){
            log.info("该职位已经存在，无法新增");
            return ReturnUtil.fail(ResultCodeEnum.POSITION_NAME_IS_INVALID);
        }
        //新增
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setStatus("0");
        int insert = positionMapper.insert(entity);
        if(insert == 0){
            log.info("添加失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    public Object deletePosition(Integer id) {
        int update = positionMapper.deleteById(id);
        if(update == 0){
            log.info("删除失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    public Object updatePosition(PositionEntity entity) {
        int update = positionMapper.updateById(entity);
        if(update == 0){
            log.info("更新失败,未知错误。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    public Object selectPositionById(Integer id) {
        PositionEntity one = positionMapper.selectById(id);
        if(one == null){
            log.info("查询为空。");
            return ReturnUtil.fail(ResultCodeEnum.POSITION_SELECT_IS_NULL);
        }
        return ReturnUtil.success(one);
    }

}
