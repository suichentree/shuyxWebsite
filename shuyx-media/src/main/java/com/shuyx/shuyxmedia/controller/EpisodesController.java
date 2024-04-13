package com.shuyx.shuyxmedia.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.EpisodesDTO;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.openfeign.MinioFeignService;
import com.shuyx.shuyxmedia.service.EpisodesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/21 9:24
 */
@Api(tags = "EpisodesController剧集接口")
@RestController
@RequestMapping("/shuyx-media/episodes")
@Slf4j
public class EpisodesController {

    @Autowired
    private EpisodesService episodesService;


    /**
     * 新增剧集接口
     * @param one
     * @return
     */
    @ApiOperation("新增剧集接口")
    @PostMapping("/add")
    public Object addEpisodes(@RequestBody EpisodesEntity one){
        log.info("/shuyx-media/episodes/add 参数 one {}",one);
        //参数校验
        if(one == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return episodesService.add(one);
    }

    /**
     * 更新剧集接口
     * @param one
     * @return
     */
    @ApiOperation("更新剧集接口")
    @PostMapping("/update")
    public Object updateEpisodes(@RequestBody EpisodesEntity one){
        log.info("/shuyx-media/episodes/update 参数 one,{}",one);
        //参数校验
        if(one == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return episodesService.update(one);
    }

    /**
     * 删除剧集接口
     * @param
     * @return
     */
    @ApiOperation("删除剧集接口")
    @DeleteMapping("/delete")
    public Object deleteEpisodes(@RequestParam("episodesId") Integer episodesId,@RequestParam("fileName") String fileName,@RequestParam("bucketName") String bucketName){
        log.info("/shuyx-media/episodes/delete, 参数 episodesId,{}",episodesId);
        log.info("参数 fileName,{}",fileName);
        log.info("参数 bucketName,{}",bucketName);
        //参数校验
        if(episodesId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return episodesService.delete(episodesId,fileName,bucketName);
    }

    /**
     * 条件查询
     * @return
     */
    @ApiOperation("条件查询剧集")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody EpisodesDTO dto){
        log.info("/shuyx-media/episodes/findBy, 参数 dto = {}",dto.getMediaId());
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return episodesService.findBy(dto);
    }

    @ApiOperation("分页查询剧集")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/episodes/pagelist, 参数 dto = {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return episodesService.pagelist(dto);
    }

}
