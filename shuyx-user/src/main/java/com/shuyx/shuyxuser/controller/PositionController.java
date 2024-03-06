package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.PositionDTO;
import com.shuyx.shuyxuser.entity.PositionEntity;
import com.shuyx.shuyxuser.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "PositionController接口")
@RestController
@RequestMapping("/shuyx-user/position")
@Slf4j
public class PositionController {
    @Autowired
    private PositionService positionService;

    /**
     * 分页查询职位
     * @param positionDTO
     * @return
     */
    @ApiOperation("分页查询职位")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody PositionDTO positionDTO){
        log.info("分页查询职位接口/pagelist,参数 positionDTO {}",positionDTO);
        //参数校验
        if(positionDTO == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return positionService.pagelist(positionDTO);
    }

    /**
     * 列表查询职位
     * @return
     */
    @ApiOperation("列表查询职位")
    @GetMapping("/postionlist")
    public Object postionlist(){
        log.info("列表查询职位接口/postionlist");
        List<PositionEntity> list = positionService.list();
        return ReturnUtil.success(list);
    }

    /**
     * 添加职位
     * @param entity
     * @return
     */
    @ApiOperation("添加职位")
    @PostMapping("/addPosition")
    public Object addPosition(@RequestBody PositionEntity entity) {
        log.info("添加职位接口/addPosition,参数 entity {}",entity);
        //参数校验
        if(entity == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return positionService.addPosition(entity);
    }

    /**
     * 根据职位ID查询职位
     * @param positionId
     * @return
     */
    @ApiOperation("根据职位ID查询职位")
    @GetMapping("/selectById")
    public Object selectById(@RequestParam Integer positionId) {
        log.info("查询职位接口/selectById,参数 positionId {}",positionId);
        //参数校验
        if(positionId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return positionService.selectPositionById(positionId);
    }

    /**
     * 职位更新
     * @param entity
     * @return
     */
    @ApiOperation("根据职位ID更新职位")
    @PostMapping("/updatePosition")
    public Object updatePosition(@RequestBody PositionEntity entity){
        log.info("职位更新接口 entity,{}",entity);
        //参数校验
        if(entity == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return positionService.updatePosition(entity);
    }

    /**
     * 职位删除
     * @param positionId
     * @return
     */
    @ApiOperation("职位删除")
    @DeleteMapping("/deletePosition")
    public Object deletePosition(@RequestParam Integer positionId){
        log.info("职位删除接口 positionId,{}",positionId);
        //参数校验
        if(positionId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return positionService.deletePosition(positionId);
    }


}
