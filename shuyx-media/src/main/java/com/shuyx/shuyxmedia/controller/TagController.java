package com.shuyx.shuyxmedia.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.TagDTO;
import com.shuyx.shuyxmedia.entity.TagEntity;
import com.shuyx.shuyxmedia.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TagController接口")
@RestController
@RequestMapping("/shuyx-media/tag")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 新增标签
     * @param one
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("/add")
    public Object addTag(@RequestBody TagEntity one){
        log.info("标签新增接口/add,参数 one {}",one);
        //参数校验
        if(one == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return tagService.addTag(one);
    }

    /**
     * 更新标签
     * @param one
     * @return
     */
    @ApiOperation("更新标签")
    @PostMapping("/update")
    public Object updateTag(@RequestBody TagEntity one){
        log.info("更新标签接口 /update 参数 one,{}",one);
        //参数校验
        if(one == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return tagService.updateTag(one);
    }

    /**
     * 删除标签
     * @param genreId
     * @return
     */
    @ApiOperation("删除标签")
    @DeleteMapping("/delete")
    public Object deleteTag(@RequestParam Integer genreId){
        log.info("标签删除接口/delete, 参数 genreId,{}",genreId);
        //参数校验
        if(genreId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return tagService.deleteTag(genreId);
    }

    /**
     * 分页查询标签
     * @return
     */
    @ApiOperation("分页查询标签")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody TagDTO dto){
        log.info("分页查询标签接口/pagelist, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return tagService.pagelist(dto);
    }

    @ApiOperation("条件查询")
    @PostMapping("/findBy")
    public Object findBy(@RequestBody TagDTO dto){
        log.info("条件查询标签接口/findBy, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return tagService.findBy(dto);
    }
}
