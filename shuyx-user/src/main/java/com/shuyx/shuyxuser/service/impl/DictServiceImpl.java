package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.DictDTO;
import com.shuyx.shuyxuser.entity.DictEntity;
import com.shuyx.shuyxuser.mapper.DictMapper;
import com.shuyx.shuyxuser.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:21
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Object update(DictDTO dto) {
        dto.setUpdateTime(new Date());
        int update = dictMapper.updateById(dto);
        if(update == 0){
            log.info("更新字典失败,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object delete(Integer id) {
        int update = dictMapper.deleteById(id);
        if(update == 0){
            log.info("删除字典失败,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object add(DictDTO dto) {
        //先查询该名称是否存在
        QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name",dto.getDictName());
        queryWrapper.eq("dict_code",dto.getDictCode());
        queryWrapper.eq("dict_label",dto.getDictLabel());
        DictEntity one = dictMapper.selectOne(queryWrapper);
        //若查询不为空
        if(one != null){
            log.info("该字典已经存在，无法新增");
            return ReturnUtil.fail(ResultCodeEnum.DICT_IS_EXIST);
        }
        //新增
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        int insert = dictMapper.insert(dto);
        if(insert == 0){
            log.info("字典添加失败,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object findBy(DictDTO dto) {
        QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<>();
        //ID查询
        if(dto.getDictId() != null){
            queryWrapper.eq("dict_id",dto.getDictId());
        }
        //名称查询
        if(StringUtils.isNotBlank(dto.getDictName())){
            queryWrapper.like("dict_name",dto.getDictName());
        }
        //字典编码查询
        if(StringUtils.isNotBlank(dto.getDictCode())){
            queryWrapper.like("dict_code",dto.getDictCode());
        }
        List<DictEntity> dictEntities = dictMapper.selectList(queryWrapper);
        return ReturnUtil.success(dictEntities);
    }

    @Override
    public Object pagelist(DictDTO dto) {
        QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<>();
        //名称查询
        if(StringUtils.isNotBlank(dto.getDictName())){
            queryWrapper.like("dict_name",dto.getDictName());
        }
        //字典编码查询
        if(StringUtils.isNotBlank(dto.getDictCode())){
            queryWrapper.like("dict_code",dto.getDictCode());
        }
        //分页查询
        Page<DictEntity> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        Page<DictEntity> pages = dictMapper.selectPage(page, queryWrapper);
        return ReturnUtil.success(pages);
    }
}
