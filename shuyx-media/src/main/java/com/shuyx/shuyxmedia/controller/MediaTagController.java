package com.shuyx.shuyxmedia.controller;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.dto.MediaTagDTO;
import com.shuyx.shuyxmedia.entity.MediaTagEntity;
import com.shuyx.shuyxmedia.service.MediaTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/10 18:22
 */
@Api(tags = "MediaTagController")
@RestController
@RequestMapping("/shuyx-media/mediaTag")
@Slf4j
public class MediaTagController {
    @Autowired
    private MediaTagService mediaTagService;

    /**
     * 批量保存或批量更新接口
     * @param one
     * @return
     */
    @ApiOperation("批量保存或批量更新接口")
    @PostMapping("/saveOrUpdateBatch")
    public Object saveOrUpdateBatch(@RequestBody MediaDTO one){
        log.info("/shuyx-media/mediaTag/saveOrUpdateBatch 参数 one,{}",one);
        //参数校验
        if(one == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaTagService.saveOrUpdateBatch(one);
    }

    /**
     * 删除接口
     * @param id
     * @return
     */
    @ApiOperation("删除接口")
    @DeleteMapping("/delete")
    public Object delete(Integer id){
        log.info("/shuyx-media/mediaTag/delete, 参数 id,{}",id);
        //参数校验
        if(id == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaTagService.delete(id);
    }

    /**
     * 条件查询
     * @return
     */
    @ApiOperation("条件查询")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody MediaTagDTO dto){
        log.info("/shuyx-media/mediaTag/findBy, 参数 dto = {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaTagService.findBy(dto);
    }


}
