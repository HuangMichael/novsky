package com.subway.service.app.sysLog;/**
 * Created by Administrator on 2016/12/6.
 */

import com.subway.dao.app.sysLog.SysLogRepository;
import com.subway.domain.app.sysLog.SysLog;
import com.subway.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统日志业务类
 *
 * @author
 * @create 2016-12-06 10:00
 **/
@Service
public class SysLogService extends BaseService {

    @Autowired
    SysLogRepository sysLogRepository;

    /**
     * @param sysLog
     * @return 保存系统日志信息
     */
    public SysLog save(SysLog sysLog) {
        return sysLogRepository.save(sysLog);
    }


}
