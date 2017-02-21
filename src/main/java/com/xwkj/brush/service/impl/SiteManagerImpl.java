package com.xwkj.brush.service.impl;

import com.xwkj.brush.domain.Site;
import com.xwkj.brush.service.SiteManager;
import com.xwkj.brush.service.common.ManagerTemplate;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RemoteProxy(name = "SiteManager")
public class SiteManagerImpl extends ManagerTemplate implements SiteManager {

    @Transactional
    @RemoteMethod
    public String addSite(String name, String url) {
        Site site = new Site();
        site.setName(name);
        site.setUrl(url);
        site.setEnable(true);
        return siteDao.save(site);
    }
}
