package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.entity.OrgEntity;

public interface OrgService extends IService<OrgEntity> {
    public Object orglist(OrgEntity org);
    public Object orgTreelist();
    public Object addOrg(OrgEntity org);
    public Object updateOrg(OrgEntity org);
    public Object deleteOrg(Integer orgId);
}
