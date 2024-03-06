package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.entity.OrgEntity;
import com.shuyx.shuyxuser.mapper.OrgMapper;
import com.shuyx.shuyxuser.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, OrgEntity> implements OrgService {

    @Autowired
    private OrgMapper orgMapper;

    /**
     * 条件查询组织机构
     * @param org
     * @return
     */
    @Override
    public Object orglist(OrgEntity org) {
        QueryWrapper<OrgEntity> queryWrapper = new QueryWrapper<>();
        //组织机构名称查询
        if(StringUtils.isNotBlank(org.getOrgName())){
            queryWrapper.like("org_name",org.getOrgName());
        }
        //状态查询
        if(org.getStatus() != null){
            queryWrapper.eq("status",org.getStatus());
        }
        List<OrgEntity> orgEntities = orgMapper.selectList(queryWrapper);
        if(orgEntities == null){
            log.info("查询失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_SELECT_FAILED);
        }
        return ReturnUtil.success(orgEntities);
    }

    /**
     * 查询全部组织机构，并返回树形数据
     * @return
     */
    @Override
    public Object orgTreelist() {
        //查询全部的组织机构
        QueryWrapper<OrgEntity> queryWrapper = new QueryWrapper<>();
        List<OrgEntity> allList = orgMapper.selectList(queryWrapper);
        //查询全部组织机构失败
        if(allList == null){
            log.info("查询数据失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_SELECT_FAILED);
        }

        //构造组织机构树形列表
        //1. 先把所有组织机构集合转换为stream
        //2. 然后找出根节点
        //3. 然后调用findChildrens迭代方法
        List<OrgEntity> treeList = allList.stream().filter(obj -> {
            //找出根节点
            return obj.getParentId() == 0;
        }).map(obj->{
            //调用findChildrens迭代方法,将当前节点的子节点设为children
            obj.setChildren(findChildrens(obj,allList));
            return obj;
        }).collect(Collectors.toList());
        return ReturnUtil.success(treeList);
    }

    /**
     * 添加组织机构
     * @param org
     * @return
     */
    @Override
    public Object addOrg(OrgEntity org) {
        //先根据名称查询组织机构是否存在
        QueryWrapper<OrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_name",org.getOrgName());
        OrgEntity one = orgMapper.selectOne(queryWrapper);
        if(one != null){
            return ReturnUtil.fail(ResultCodeEnum.ORGNAME_IS_INVALID);
        }

        //新增组织机构
        org.setCreateTime(new Date());
        org.setUpdateTime(new Date());
        int insert = orgMapper.insert(org);
        if(insert != 1){
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
        }

        //设置新组织机构的路径
        //新组织机构的路径 = 上级组织机构的路径 + 新组织机构的id + “/“
        OrgEntity parent = orgMapper.selectById(org.getParentId());
        org.setOrgPath(parent.getOrgPath()+org.getOrgId()+"/");
        int update = orgMapper.updateById(org);
        if(update !=1 ){
            log.info("新组织机构的路径添加失败");
            return ReturnUtil.fail(ResultCodeEnum.ORGPATH_ADD_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 更新组织机构信息
     * @param
     * @return
     */
    @Override
    public Object updateOrg(OrgEntity currentOrg) {
        //查询数据库中当前组织机构信息
        OrgEntity tableOrg = orgMapper.selectById(currentOrg.getOrgId());
        //是否更新上级组织机构？
        if(currentOrg.getParentId().equals(tableOrg.getParentId())){
            //表示没有更新上级组织机构
            int update = orgMapper.updateById(currentOrg);
            if(update!=1){
                log.info("更新失败1");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
            }
        }else if(currentOrg.getParentId().equals(tableOrg.getOrgId())){
            //表示选择自己为上级组织机构
            return ReturnUtil.fail(ResultCodeEnum.PARENT_ORG_IS_INVALID);
        }else{
            //更新了上级组织机构
            //查询要更新的上级组织机构信息
            OrgEntity newUpOrg = orgMapper.selectById(currentOrg.getParentId());
            //判断当前组织机构是否是叶子节点
            List<OrgEntity> list = orgMapper.selectList(new QueryWrapper<OrgEntity>().eq("parent_id", currentOrg.getOrgId()));

            //当前节点的新路径 = 新的上级组织路径 + 组织id + "/"
            String newPath = newUpOrg.getOrgPath()+tableOrg.getOrgId()+"/";
            if(list != null && list.size() == 0){
                //是叶子节点，直接更新
                //更新为新的组织路径
                currentOrg.setOrgPath(newPath);
                currentOrg.setParentId(newUpOrg.getOrgId());
                int update = orgMapper.updateById(currentOrg);
                if(update!=1){
                    log.info("更新失败2");
                    return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
                }
            }else{
                //不是叶子节点
                //1. 先更新当前节点的上级id和路径
                //2. 然后更新当前节点的全部下级节点中的路径（无须更新下级节点的上级id,因为上级id没有变化）

                //这是当前节点的路径，然后根据路径找出当前节点和下面的所有字节点（获取当前节点的路径A，并查询以该路径A为开头的路径的组织机构）
                String oldPath = tableOrg.getOrgPath();
                List<OrgEntity> list1 = orgMapper.selectList(new QueryWrapper<OrgEntity>().likeRight("org_path", oldPath));
                //遍历列表
                for (OrgEntity obj : list1) {
                    //判断是否是当前节点
                    if(obj.getOrgId() == tableOrg.getOrgId()){
                        //这是当前节点，先更新当前节点的上级id和路径
                        currentOrg.setOrgPath(newPath);
                        currentOrg.setParentId(newUpOrg.getOrgId());
                        int update = orgMapper.updateById(currentOrg);
                        if(update!=1){
                            log.info("更新失败3");
                            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
                        }
                    }else{
                        //这时当前节点下的所有子节点。此时只用更新路径即可
                        //更新路径
                        obj.setOrgPath(obj.getOrgPath().replace(oldPath,newPath));
                        int update = orgMapper.updateById(obj);
                        if(update!=1){
                            log.info("更新失败4");
                            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
                        }
                    }
                }
            }
        }
        return ReturnUtil.success();
    }

    /**
     * 删除组织机构
     * @param orgId
     * @return
     */
    @Override
    public Object deleteOrg(Integer orgId) {
        //先查询该组织机构是否有下级组织机构
        List<OrgEntity> list = orgMapper.selectList(new QueryWrapper<OrgEntity>().eq("parent_id", orgId));
        if(list != null && list.size() > 0){
            //有下级组织机构，不可以删除
            log.info("该组织机构存在下级组织机构，无法删除。");
            return ReturnUtil.fail(ResultCodeEnum.CHILD_ORG_IS_EXITS);
        }
        int i = orgMapper.deleteById(orgId);
        if(i == 0){
            //删除失败
            log.info("删除失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
        }
        return ReturnUtil.success();
    }

    /**
     * 递归方法findChildrens,用于找到当前节点的子节点
     * @param node 当前节点
     * @param allNode 所有节点
     * @return
     */
    public List<OrgEntity> findChildrens(OrgEntity node,List<OrgEntity> allNode){
        List<OrgEntity> collect = allNode.stream().filter(obj -> {
            return Objects.equals(obj.getParentId(), node.getOrgId());
        }).map(obj -> {
            obj.setChildren(findChildrens(obj, allNode));
            return obj;
        }).collect(Collectors.toList());
        return collect;
    }

}
