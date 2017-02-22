package com.xwkj.brush.component;

import com.xwkj.brush.dao.SiteDao;
import com.xwkj.brush.domain.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class BrushComponent {

    @Autowired
    private SiteDao siteDao;

    private boolean brushing = false;
    private List<Site> sites = null;

    @Scheduled(fixedRate = 1000 * 10)
    public void schedule() {
        // If brush system is not run now, start brush.
        if (!brushing) {
            // Connect PPPoE network. networksetup -connectpppoeservice PPPoE
            sudo("pon dsl-provider");
            sites = siteDao.findEnable();
            System.out.println(sites.size() + " sites is enable to brush.");
            brushing = true;
            return;
        }
        // If index is sites.size() - 1, stop brush.
        if (sites.size() == 0) {
            // Disconnect PPPoE network. networksetup -connectpppoeservice PPPoE
            sudo("poff -a");
            brushing = false;
            return;
        }
        // If brush system is running, open a site.
        if (brushing) {
//            run("wmctrl -c chrom");
            Site site = sites.get(0);
            System.out.println("Site: " + site.getUrl());
            run("xdg-open " + site.getUrl());
            sites.remove(site);
        }

    }

    protected static String sudoPasswd = "123";

    public static void sudo(String cmd) {
        String[] cmds = {"/bin/bash", "-c", "echo \"" + sudoPasswd + "\" | sudo -S " + cmd};
        exec(cmds);
    }

    public static void run(String cmd) {
        String[] cmds = {"/bin/bash", "-c",  cmd};
        exec(cmds);
    }

    public static void exec(String [] cmds) {
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
