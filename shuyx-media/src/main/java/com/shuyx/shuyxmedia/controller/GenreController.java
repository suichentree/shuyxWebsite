package com.shuyx.shuyxmedia.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxmedia.dto.GenreDTO;
import com.shuyx.shuyxmedia.entity.GenreEntity;
import com.shuyx.shuyxmedia.service.GenreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "GenreController接口")
@RestController
@RequestMapping("/shuyx-media/genre")
@Slf4j
public class GenreController {

    @Autowired
    private GenreService genreService;

    /**
     * 新增类型
     * @param one
     * @return
     */
    @ApiOperation("类型新增")
    @PostMapping("/addGenre")
    public Object addGenre(@RequestBody GenreEntity one){
        log.info("类型新增接口/addGenre,参数 one {}",one);
        //参数校验
        if(one == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return genreService.addGenre(one);
    }

    /**
     * 更新类型
     * @param one
     * @return
     */
    @ApiOperation("更新类型")
    @PostMapping("/updateGenre")
    public Object updateGenre(@RequestBody GenreEntity one){
        log.info("更新类型接口 /updateGenre 参数 one,{}",one);
        //参数校验
        if(one == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return genreService.updateGenre(one);
    }

    /**
     * 删除类型
     * @param genreId
     * @return
     */
    @ApiOperation("删除类型")
    @DeleteMapping("/deleteGenre")
    public Object deleteGenre(@RequestParam Integer genreId){
        log.info("类型删除接口/deleteGenre, 参数 genreId,{}",genreId);
        //参数校验
        if(genreId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return genreService.deleteGenre(genreId);
    }

    /**
     * 分页查询类型
     * @return
     */
    @ApiOperation("分页查询类型")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody GenreDTO dto){
        log.info("分页查询类型接口/pagelist, 参数 dto,{}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return genreService.pagelist(dto);
    }

}
