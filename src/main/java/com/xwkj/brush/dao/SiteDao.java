package com.xwkj.brush.dao;

import com.xwkj.brush.domain.Site;
import com.xwkj.common.hibernate.support.CrudDao;

import java.util.List;

public interface SiteDao extends CrudDao<Site> {

    /**
     * Find enable sites.
     * @return
     */
    List<Site> findEnable();

}
