package com.shuyx.shuyxmedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.GenreDTO;
import com.shuyx.shuyxmedia.entity.GenreEntity;
import com.shuyx.shuyxmedia.mapper.GenreMapper;
import com.shuyx.shuyxmedia.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GenreServiceImpl extends ServiceImpl<GenreMapper, GenreEntity> implements GenreService {

    @Autowired
    private GenreMapper genreMapper;

    @Override
    public Object addGenre(GenreEntity one) {
        //新增类型
        int insert = genreMapper.insert(one);
        if(insert == 0){
            log.info("类型新增失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object updateGenre(GenreEntity one) {
        int update = genreMapper.updateById(one);
        if(update == 0){
            log.info("类型更新失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object deleteGenre(Integer id) {
        int i = genreMapper.deleteById(id);
        if(i == 0){
            log.info("类型删除失败,未知错误，请管理员查询日志信息。");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object pagelist(GenreDTO dto) {
        //条件查询
        QueryWrapper<GenreEntity> queryWrapper = new QueryWrapper<>();
        if(dto.getGenreId() != null){
            queryWrapper.eq("genre_id",dto.getGenreId());
        }
        if(StringUtils.isNotBlank(dto.getGenreName())){
            queryWrapper.like("genre_name",dto.getGenreName());
        }
        if(StringUtils.isNotBlank(dto.getType())){
            queryWrapper.eq("type",dto.getType());
        }
        //如果没有分页参数，那么就全查
        if(dto.getPageNum() == null && dto.getPageSize() == null){
            List<GenreEntity> list = genreMapper.selectList(queryWrapper);
            return ReturnUtil.success(list);
        }else{
            //使用PageHelper分页插件来进行分页操作
            PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
            List<GenreEntity> list = genreMapper.selectList(queryWrapper);
            PageInfo pageInfo = new PageInfo<>(list);
            return ReturnUtil.success(pageInfo);
        }
    }
}
