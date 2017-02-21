package com.xwkj.brush.component;

import com.xwkj.brush.dao.SiteDao;
import com.xwkj.brush.domain.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class BrushComponent {

    @Autowired
    private SiteDao siteDao;

    private boolean brushing = false;
    private List<Site> sites = null;
    private int index = 0;

    @Scheduled(fixedRate = 1000 * 10)
    public void schedule() {
        // If brush system is not run now, start brush.
        if (!brushing) {
            // Connect PPPoE network. networksetup -connectpppoeservice PPPoE
            exec("networksetup -connectpppoeservice PPPoE");
            sites = siteDao.findAll();
            brushing = true;
        }
        // If brush system is running, open a site.
        if (brushing) {
            Site site = sites.get(index);
            index++;
            System.out.print(site.getUrl());
            exec("open " + site.getUrl());
        }
        // If index is sites.size() - 1, stop brush.
        if (index == sites.size() - 1) {
            sites = null;
            index = 0;
            // Disconnect PPPoE network. networksetup -connectpppoeservice PPPoE
            exec("networksetup -connectpppoeservice PPPoE");
        }
    }

    public String exec(String cmd) {
        InputStream inputStream = null;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            inputStream = process.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
            String result = read.readLine();
            System.out.println("INFO: " + result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
