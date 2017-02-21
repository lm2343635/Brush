package com.xwkj.brush.component;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BrushComponent {

    @Scheduled(fixedRate = 1000 * 10)
    public void schedule() {

    }

}
