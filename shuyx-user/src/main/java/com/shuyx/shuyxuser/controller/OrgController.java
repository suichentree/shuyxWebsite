package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.entity.OrgEntity;
import com.shuyx.shuyxuser.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "OrgController接口")
@RestController
@RequestMapping("/shuyx-user/org")
@Slf4j
public class OrgController {

    @Autowired
    private OrgService orgService;

    /**
     * 组织机构查询
     * @param org
     * @return
     */
    @ApiOperation("组织机构查询")
    @GetMapping("/orglist")
    public Object orglist(OrgEntity org){
        log.info("组织机构查询接口/orglist,参数 org {}",org);
        //参数校验
        if(org == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return orgService.orglist(org);
    }

    /**
     * 组织机构树形查询
     * @return
     */
    @ApiOperation("组织机构树形查询")
    @GetMapping("/orgTreelist")
    public Object orgTreelist(){
        log.info("组织机构树形查询接口 /orgTreelist");
        return orgService.orgTreelist();
    }

    /**
     * 添加组织机构
     * @param org
     * @return
     */
    @ApiOperation("添加组织机构")
    @PostMapping("/addOrg")
    public Object addOrg(@RequestBody OrgEntity org){
        log.info("添加组织机构接口 /addOrg org,{}",org);
        if(org == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return orgService.addOrg(org);
    }

    /**
     * 更新组织机构
     * @param org
     * @return
     */
    @ApiOperation("更新组织机构")
    @PostMapping("/updateOrg")
    public Object updateOrg(@RequestBody OrgEntity org){
        log.info("更新组织机构接口 /updateOrg org,{}",org);
        if(org == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return orgService.updateOrg(org);
    }

    /**
     * 删除
     * @param orgId
     * @return
     */
    @ApiOperation("组织机构删除")
    @DeleteMapping("/deleteOrg")
    public Object deleteOrg(@RequestParam Integer orgId){
        log.info("组织机构删除接口 orgId,{}",orgId);
        //参数校验
        if(orgId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return orgService.deleteOrg(orgId);
    }
}
