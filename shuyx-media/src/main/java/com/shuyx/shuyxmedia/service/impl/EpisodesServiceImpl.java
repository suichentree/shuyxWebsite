package com.shuyx.shuyxmedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.EpisodesDTO;
import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.mapper.EpisodesMapper;
import com.shuyx.shuyxmedia.service.EpisodesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/21 0:20
 */
@Service
@Slf4j
public class EpisodesServiceImpl extends ServiceImpl<EpisodesMapper,EpisodesEntity> implements EpisodesService {
    @Autowired
    private EpisodesMapper episodesMapper;

    @Override
    public Object add(EpisodesEntity one) {
        //查询剧集序号是否已存在
        QueryWrapper<EpisodesEntity> episodesEntityQueryWrapper = new QueryWrapper<>();
        episodesEntityQueryWrapper.eq("episodes_number",one.getEpisodesNumber());
        episodesEntityQueryWrapper.eq("media_id",one.getMediaId());

        EpisodesEntity episodesEntity = episodesMapper.selectOne(episodesEntityQueryWrapper);
        if(episodesEntity != null){
            log.info("剧集新增失败,该剧集编号已存在，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        //新增
        int insert = episodesMapper.insert(one);
        if(insert == 0){
            log.info("剧集新增失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object update(EpisodesEntity one) {
        int update = episodesMapper.updateById(one);
        if(update == 0){
            log.info("剧集更新失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object findBy(EpisodesDTO dto) {
        //条件查询
        QueryWrapper<EpisodesEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getMediaId() != null){
            queryWrapper.eq("media_id",dto.getMediaId());
        }
        if(StringUtils.isNotBlank(dto.getEpisodesName())){
            queryWrapper.like("episodes_name",dto.getEpisodesName());
        }
        if(dto.getEpisodesNumber() != null){
            queryWrapper.eq("episodes_number",dto.getEpisodesNumber());
        }
        queryWrapper.orderByAsc("episodes_number");
        List<EpisodesEntity> episodesEntities = episodesMapper.selectList(queryWrapper);
        return ReturnUtil.success(episodesEntities);
    }

    @Override
    public Object delete(Integer id) {
        int i = episodesMapper.deleteById(id);
        if(i == 0){
            log.info("剧集删除失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }
}
