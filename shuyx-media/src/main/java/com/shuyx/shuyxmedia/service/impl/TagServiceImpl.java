package com.shuyx.shuyxmedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.TagDTO;
import com.shuyx.shuyxmedia.entity.TagEntity;
import com.shuyx.shuyxmedia.mapper.TagMapper;
import com.shuyx.shuyxmedia.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Object addTag(TagEntity one) {
        //新增类型
        int insert = tagMapper.insert(one);
        if(insert == 0){
            log.info("类型新增失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object updateTag(TagEntity one) {
        int update = tagMapper.updateById(one);
        if(update == 0){
            log.info("类型更新失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object deleteTag(Integer id) {
        int i = tagMapper.deleteById(id);
        if(i == 0){
            log.info("类型删除失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object pagelist(TagDTO dto) {
        //条件查询
        QueryWrapper<TagEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getTagId() != null){
            queryWrapper.eq("tag_id",dto.getTagId());
        }
        if(StringUtils.isNotBlank(dto.getTagName())){
            queryWrapper.like("tag_name",dto.getTagName());
        }
        if(StringUtils.isNotBlank(dto.getTagType())){
            queryWrapper.eq("tag_type",dto.getTagType());
        }

        //使用PageHelper分页插件来进行分页操作
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<TagEntity> list = tagMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo<>(list);
        return ReturnUtil.success(pageInfo);
    }

    @Override
    public Object findBy(TagDTO dto) {
        //条件查询
        QueryWrapper<TagEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getTagId() != null){
            queryWrapper.eq("tag_id",dto.getTagId());
        }
        if(StringUtils.isNotBlank(dto.getTagName())){
            queryWrapper.like("tag_name",dto.getTagName());
        }
        if(StringUtils.isNotBlank(dto.getTagType())){
            queryWrapper.eq("tag_type",dto.getTagType());
        }
        List<TagEntity> list = tagMapper.selectList(queryWrapper);
        return ReturnUtil.success(list);
    }

    @Override
    public Object findByMediaId(Integer mediaId) {
        List<TagEntity> tagByMediaId = tagMapper.findTagByMediaId(mediaId);
        return ReturnUtil.success(tagByMediaId);
    }
}
