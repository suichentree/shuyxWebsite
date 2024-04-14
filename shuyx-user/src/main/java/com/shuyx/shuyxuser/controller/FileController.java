package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.FileDTO;
import com.shuyx.shuyxuser.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:18
 */
@Api(tags = "FileController接口")
@RestController
@RequestMapping("/shuyx-user/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 分页查询文件
     * @param dto
     * @return
     */
    @ApiOperation("分页查询文件")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody FileDTO dto){
        log.info("/shuyx-user/file/pagelist,参数 FileDTO {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return fileService.pagelist(dto);
    }

    /**
     * 条件查询文件
     * @return
     */
    @ApiOperation("条件查询文件")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody FileDTO dto){
        log.info("/shuyx-user/file/findBy, dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return fileService.findBy(dto);
    }

    /**
     * 添加文件
     * @param dto
     * @return
     */
    @ApiOperation("添加文件")
    @PostMapping("/add")
    public Object add(@RequestBody FileDTO dto) {
        log.info("/shuyx-user/file/add,参数 dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return fileService.add(dto);
    }

    /**
     * 文件更新
     * @param dto
     * @return
     */
    @ApiOperation("更新文件")
    @PostMapping("/update")
    public Object update(@RequestBody FileDTO dto){
        log.info("/shuyx-user/file/update, dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return fileService.update(dto);
    }

    /**
     * 文件删除
     * @param
     * @return
     */
    @ApiOperation("文件删除")
    @DeleteMapping("/delete")
    public Object delete(@RequestParam("fileId") Integer fileId,@RequestParam("fileName") String fileName,@RequestParam("bucketName") String bucketName){
        log.info("/shuyx-user/file/delete,fileId,{}",fileId);
        //参数校验
        if(fileId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return fileService.delete(fileId,fileName,bucketName);
    }

}
