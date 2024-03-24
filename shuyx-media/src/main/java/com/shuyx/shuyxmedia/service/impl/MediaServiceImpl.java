package com.shuyx.shuyxmedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.dto.MediaGenreDTO;
import com.shuyx.shuyxmedia.entity.GenreEntity;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import com.shuyx.shuyxmedia.mapper.MediaGenreMapper;
import com.shuyx.shuyxmedia.mapper.MediaMapper;
import com.shuyx.shuyxmedia.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MediaServiceImpl extends ServiceImpl<MediaMapper, MediaEntity> implements MediaService {

    @Autowired
    private MediaMapper mediaMapper;
    @Autowired
    private MediaGenreMapper mediaGenreMapper;

    @Override
    public Object addMedia(MediaDTO one) {
        //新增媒体
        one.setCreateTime(new Date());
        one.setUpdateTime(new Date());
        //修饰数据
        List<GenreEntity> genreDTOList1 = one.getGenreDTOList();
        StringBuilder sb = new StringBuilder();
        if(genreDTOList1 !=null && genreDTOList1.size() > 0){
            for (GenreEntity dto:genreDTOList1) {
                //拼接媒体标签字段
                sb = sb.append('/').append(dto.getGenreName());
            }
        }
        one.setMediaTag(sb.toString());
        //插入数据
        int insert = mediaMapper.insert(one);
        if(insert == 0){
            log.info("媒体新增失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }

        //新增媒体类型
        List<MediaGenreDTO> mediaGenreList = new ArrayList<>();
        List<GenreEntity> genreDTOList2 = one.getGenreDTOList();
        if(genreDTOList2 !=null && genreDTOList2.size() > 0){
            for (GenreEntity dto:genreDTOList2) {
                MediaGenreDTO obj = new MediaGenreDTO(one.getMediaId(),dto.getGenreId());
                mediaGenreList.add(obj);
            }
            //插入数据
            Integer integer = mediaGenreMapper.batchInsertMediaGenre(mediaGenreList);
            if(integer == 0){
                log.info("媒体类型新增失败,未知错误，请管理员查询日志信息。");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
            }
        }

        return ReturnUtil.success();
    }

    @Override
    public Object updateMedia(MediaDTO one) {
        //更新媒体表,先修饰数据
        List<GenreEntity> genreDTOList1 = one.getGenreDTOList();
        StringBuilder sb = new StringBuilder();
        if(genreDTOList1 !=null && genreDTOList1.size() > 0){
            for (GenreEntity dto:genreDTOList1) {
                //拼接媒体标签字段
                sb = sb.append('/').append(dto.getGenreName());
            }
        }
        one.setMediaTag(sb.toString());
        one.setUpdateTime(new Date());
        int update = mediaMapper.updateById(one);
        if(update == 0){
            log.info("媒体更新失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }

        //更新媒体类型表,先删除后新增
        List<MediaGenreDTO> mediaGenreList = new ArrayList<>();
        List<GenreEntity> genreDTOList2 = one.getGenreDTOList();
        if(genreDTOList2 !=null && genreDTOList2.size() > 0){
            //先删除之前的媒体类型
            Integer integer1 = mediaGenreMapper.deleteMediaGenreByMediaId(one.getMediaId());
            //然后新增现在的类型
            for (GenreEntity dto:genreDTOList2) {
                MediaGenreDTO obj = new MediaGenreDTO(one.getMediaId(),dto.getGenreId());
                mediaGenreList.add(obj);
            }
            //插入数据
            Integer integer = mediaGenreMapper.batchInsertMediaGenre(mediaGenreList);
            if(integer == 0){
                log.info("媒体类型新增失败,未知错误，请管理员查询日志信息。");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
            }
        }
        return ReturnUtil.success();
    }

    @Override
    public Object deleteMedia(Integer id) {
        int i = mediaMapper.deleteById(id);
        if(i == 0){
            log.info("删除失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object pagelist(MediaDTO dto) {
        //媒体条件查询
        QueryWrapper<MediaEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getMediaId() != null){
            queryWrapper.eq("media_id",dto.getMediaId());
        }
        if(StringUtils.isNotBlank(dto.getMediaName())){
            queryWrapper.like("media_name",dto.getMediaName());
        }
        if(StringUtils.isNotBlank(dto.getMediaType())){
            queryWrapper.eq("media_type",dto.getMediaType());
        }

        //使用PageHelper分页插件来进行分页操作
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        List<MediaEntity> list = mediaMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo<>(list);
        return ReturnUtil.success(pageInfo);
    }

    public Object pageFindMediaAndGenre(MediaDTO dto) {
        //使用PageHelper分页插件来进行分页操作
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        if(dto.getGenreIds() != null && dto.getGenreIds().length > 0){
            List<MediaEntity> list = mediaMapper.pageFindMediaAndGenre(dto.getGenreIds(),dto.getGenreIds().length);
            PageInfo pageInfo = new PageInfo<>(list);
            return ReturnUtil.success(pageInfo);
        }else{
            PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
            List<MediaEntity> list = mediaMapper.selectList(new QueryWrapper<>());
            PageInfo pageInfo = new PageInfo<>(list);
            return ReturnUtil.success(pageInfo);
        }
    }

    @Override
    public Object findBy(MediaDTO dto) {
        //媒体条件查询
        QueryWrapper<MediaEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getMediaId() != null){
            queryWrapper.eq("media_id",dto.getMediaId());
        }
        if(StringUtils.isNotBlank(dto.getMediaName())){
            queryWrapper.like("media_name",dto.getMediaName());
        }
        if(StringUtils.isNotBlank(dto.getMediaType())){
            queryWrapper.eq("media_type",dto.getMediaType());
        }
        List<MediaDTO> list = mediaMapper.findMediaAndGenre(dto);
        return ReturnUtil.success(list);
    }

}
