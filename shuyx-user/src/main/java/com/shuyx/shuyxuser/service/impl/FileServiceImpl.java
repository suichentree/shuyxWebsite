package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.FileDTO;
import com.shuyx.shuyxuser.entity.FileEntity;
import com.shuyx.shuyxuser.mapper.FileMapper;
import com.shuyx.shuyxuser.openfeign.MinioFeignService;
import com.shuyx.shuyxuser.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:21
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private MinioFeignService minioFeignService;

    @Override
    public Object update(FileDTO dto) {
        int update = fileMapper.updateById(dto);
        if(update == 0){
            log.info("更新失败,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object delete(Integer id,String fileName,String bucketName) {
        try{
            int update = fileMapper.deleteById(id);
            if(update == 0){
                log.info("删除失败,未知错误。请查询日志");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
            }
            //删除minio中的文件
            minioFeignService.delete(fileName, bucketName);
        }catch (Exception e){
            e.printStackTrace();
            log.info("删除文件异常,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object add(FileDTO dto) {
        //先查询该名称是否存在
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name",dto.getFileName());
        FileEntity one = fileMapper.selectOne(queryWrapper);
        //若查询不为空
        if(one != null){
            log.info("该文件名称已经存在，无法新增");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        //新增
        int insert = fileMapper.insert(dto);
        if(insert == 0){
            log.info("添加文件失败,未知错误。请查询日志");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object findBy(FileDTO dto) {
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        //ID查询
        if(dto.getFileId() != null){
            queryWrapper.eq("file_id",dto.getFileId());
        }
        //名称查询
        if(StringUtils.isNotBlank(dto.getFileName())){
            queryWrapper.like("file_name",dto.getFileName());
        }
        List<FileEntity> fileEntities = fileMapper.selectList(queryWrapper);
        return ReturnUtil.success(fileEntities);
    }

    @Override
    public Object pagelist(FileDTO dto) {
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        //名称查询
        if(StringUtils.isNotBlank(dto.getFileName())){
            queryWrapper.like("file_name",dto.getFileName());
        }
        //分页查询
        Page<FileEntity> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        Page<FileEntity> pagelist = fileMapper.selectPage(page, queryWrapper);
        return ReturnUtil.success(pagelist);
    }
}
