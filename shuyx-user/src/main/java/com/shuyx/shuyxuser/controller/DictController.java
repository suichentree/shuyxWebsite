package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.DictDTO;
import com.shuyx.shuyxuser.dto.PositionDTO;
import com.shuyx.shuyxuser.entity.PositionEntity;
import com.shuyx.shuyxuser.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:18
 */
@Api(tags = "DictController接口")
@RestController
@RequestMapping("/shuyx-user/dict")
@Slf4j
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 分页查询字典
     * @param dto
     * @return
     */
    @ApiOperation("分页查询字典")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody DictDTO dto){
        log.info("/shuyx-user/dict/pagelist,参数 DictDTO {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return dictService.pagelist(dto);
    }

    /**
     * 条件查询字典
     * @return
     */
    @ApiOperation("条件查询字典")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody DictDTO dto){
        log.info("/shuyx-user/dict/findBy, dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return dictService.findBy(dto);
    }

    /**
     * 添加字典
     * @param dto
     * @return
     */
    @ApiOperation("添加字典")
    @PostMapping("/add")
    public Object add(@RequestBody DictDTO dto) {
        log.info("/shuyx-user/dict/add,参数 dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return dictService.add(dto);
    }

    /**
     * 字典更新
     * @param dto
     * @return
     */
    @ApiOperation("更新字典")
    @PostMapping("/update")
    public Object update(@RequestBody DictDTO dto){
        log.info("/shuyx-user/dict/update, dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return dictService.update(dto);
    }

    /**
     * 字典删除
     * @param id
     * @return
     */
    @ApiOperation("字典删除")
    @DeleteMapping("/delete")
    public Object delete(@RequestParam Integer id){
        log.info("/shuyx-user/dict/delete,id,{}",id);
        //参数校验
        if(id == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return dictService.delete(id);
    }


}
