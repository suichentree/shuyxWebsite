package com.shuyx.shuyxmedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.dto.MediaTagDTO;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import com.shuyx.shuyxmedia.entity.MediaTagEntity;
import com.shuyx.shuyxmedia.entity.TagEntity;
import com.shuyx.shuyxmedia.exception.MyException;
import com.shuyx.shuyxmedia.mapper.MediaMapper;
import com.shuyx.shuyxmedia.mapper.MediaTagMapper;
import com.shuyx.shuyxmedia.mapper.TagMapper;
import com.shuyx.shuyxmedia.service.MediaTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/10 18:20
 */
@Service
@Slf4j
public class MediaTagServiceImpl extends ServiceImpl<MediaTagMapper, MediaTagEntity> implements MediaTagService {

    @Autowired
    private MediaTagMapper mediaTagMapper;
    @Autowired
    private MediaMapper mediaMapper;

    @Override
    public Object delete(Integer id) {
        int i = mediaTagMapper.deleteById(id);
        if (i == 0) {
            log.info("删除失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object findBy(MediaTagDTO dto) {
        QueryWrapper<MediaTagEntity> queryWrapper = new QueryWrapper<>();
        if (dto.getMediaId() != null) {
            queryWrapper.eq("media_id", dto.getMediaId());
        }
        if (dto.getTagId() != null) {
            queryWrapper.eq("tag_id", dto.getTagId());
        }
        List<MediaTagEntity> mediaTagEntities = mediaTagMapper.selectList(queryWrapper);
        return ReturnUtil.success(mediaTagEntities);
    }

    /**
     * 批量保存或更新标签
     * 1. 先更新媒体表的标签字段
     * 2. 然后根据媒体id，删除媒体标签表中的所有旧的标签
     * 3. 然后批量新增媒体标签到媒体标签表中
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Object saveOrUpdateBatch(MediaDTO dto) {
        try {
            //先根据媒体id查询媒体信息
            MediaEntity one = mediaMapper.selectById(dto.getMediaId());
            if (one == null) {
                log.info("没有查询到该媒体,无法更新。请管理员查询日志信息。");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
            }
            //更新该媒体的标签字段信息
            List<TagEntity> tagList = dto.getTagList();
            StringBuilder sb = new StringBuilder();
            if (tagList != null && tagList.size() > 0) {
                for (TagEntity tag : tagList) {
                    //拼接媒体标签字段
                    sb = sb.append('/').append(tag.getTagName());
                }
            } else {
                sb.append("暂无");
            }
            one.setMediaTag(sb.toString());
            one.setUpdateTime(new Date());
            int update = mediaMapper.updateById(one);
            if (update == 0) {
                log.info("媒体更新失败,未知错误，请管理员查询日志信息。");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
            }

            //批量更新或批量新增媒体标签表记录
            List<MediaTagDTO> mediaTagList = new ArrayList<>();
            //1. 先根据mediaId批量删除标签
            mediaTagMapper.deleteMediaTagByMediaId(dto.getMediaId());
            //2. 然后批量新增标签。先组装MediaTagDTO列表
            Integer[] tagIds = dto.getTagIds();
            if(tagIds.length > 0){
                for (Integer i : tagIds) {
                    MediaTagDTO obj = new MediaTagDTO(one.getMediaId(), i);
                    mediaTagList.add(obj);
                }
                //3. 批量插入新的标签数据
                mediaTagMapper.batchInsertMediaTag(mediaTagList);
            }
        } catch (Exception e) {
            log.info("媒体标签更新失败,未知错误，请管理员查询日志信息。");
            e.printStackTrace();
            throw new MyException(500,"媒体标签更新异常");
        }
        return ReturnUtil.success();
    }


}
