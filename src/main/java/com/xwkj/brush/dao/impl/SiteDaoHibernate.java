package com.xwkj.brush.dao.impl;

import com.xwkj.brush.dao.SiteDao;
import com.xwkj.brush.domain.Site;
import com.xwkj.common.hibernate.support.PageHibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class SiteDaoHibernate extends PageHibernateDaoSupport<Site> implements SiteDao {

    public SiteDaoHibernate() {
        setClass(Site.class);
    }

}
