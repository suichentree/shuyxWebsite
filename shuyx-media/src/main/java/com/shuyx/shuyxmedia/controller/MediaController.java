package com.shuyx.shuyxmedia.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.MediaDTO;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import com.shuyx.shuyxmedia.service.MediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "MediaControler接口")
@RestController
@RequestMapping("/shuyx-media/media")
@Slf4j
public class MediaController {

    @Autowired
    private MediaService mediaService;

    /**
     * 新增媒体
     * @param one
     * @return
     */
    @ApiOperation("媒体新增")
    @PostMapping("/addMedia")
    public Object addMedia(@RequestBody MediaDTO one){
        log.info("/shuyx-media/media/addMedia,参数 one {}",one);
        //参数校验
        if(one == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.addMedia(one);
    }


    /**
     * 更新媒体
     * @param one
     * @return
     */
    @ApiOperation("更新媒体")
    @PostMapping("/updateMedia")
    public Object updateMedia(@RequestBody MediaDTO one){
        log.info("/shuyx-media/media/updateMedia 参数 one,{}",one);
        //参数校验
        if(one == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.updateMedia(one);
    }

    /**
     * 删除媒体
     * @param mediaId
     * @return
     */
    @ApiOperation("删除媒体")
    @DeleteMapping("/deleteMedia")
    public Object deleteMedia(@RequestParam Integer mediaId){
        log.info("/shuyx-media/media/deleteMedia, 参数 mediaId,{}",mediaId);
        //参数校验
        if(mediaId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.deleteMedia(mediaId);
    }

    /**
     * 分页查询
     * 如果没有分页参数，那么就普通查询。有分页参数，按分页查询处理
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/media/pagelist, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.pagelist(dto);
    }

    /**
     * 分页查询2
     * 如果没有分页参数，那么就普通查询。有分页参数，按分页查询处理
     * @return
     */
    @ApiOperation("分页查询2")
    @PostMapping("/pageFindMediaAndGenre")
    public Object pageFindMediaAndGenre(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/media/pageFindMediaAndGenre, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.pageFindMediaAndGenre(dto);
    }

    /**
     * 条件查询
     * @return
     */
    @ApiOperation("条件查询")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/media/findBy, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.findBy(dto);
    }


    /**
     * 联查媒体及其类型
     * @return
     */
    @ApiOperation("联查媒体及其类型")
    @PostMapping("/findMediaAndGenre")
    public Object findMediaAndGenre(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/media/findMediaAndGenre, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.findMediaAndGenre(dto);
    }

    /**
     * 联查媒体及其剧集
     * @return
     */
    @ApiOperation("联查媒体及其剧集")
    @PostMapping("/findMediaAndEpisodes")
    public Object findMediaAndEpisodes(@RequestBody MediaDTO dto){
        log.info("/shuyx-media/media/findMediaAndEpisodes, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return mediaService.findMediaAndEpisodes(dto);
    }

}
