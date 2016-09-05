package com.linkbit.beidou.tasks;

/**
 * Created by Administrator on 2016/9/5.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 维修任务状态定时更新
 *
 * @author huangbin
 * @create 2016-09-05 14:08
 **/
@Component
public class FixtTaskStatusSchedule {

    private static final Logger log = LoggerFactory.getLogger(FixtTaskStatusSchedule.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void updateFixTaskStatus() {
        log.info("定时更新任务状态---- {}", dateFormat.format(new Date()));
    }
}
