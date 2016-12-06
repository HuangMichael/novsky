package com.subway.liseners;

import com.subway.utils.DateUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Date;

/**
 * Created by huangbin on 2016/7/18.
 */
public class AppilicationSessionExpire implements ApplicationListener<ContextClosedEvent> {

    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(DateUtils.convertDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss") + "-------------ContextClosedEvent---------------- ");
    }
}
