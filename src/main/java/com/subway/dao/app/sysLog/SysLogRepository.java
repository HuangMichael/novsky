package com.subway.dao.app.sysLog;

import com.subway.domain.app.sysLog.SysLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by huangbin on 2016/12/6.
 */
public interface SysLogRepository extends CrudRepository<SysLog, Long>, PagingAndSortingRepository<SysLog, Long> {


}
