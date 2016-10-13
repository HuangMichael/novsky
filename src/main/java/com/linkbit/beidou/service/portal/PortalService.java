package com.linkbit.beidou.service.portal;

/**
 * Created by Administrator on 2016/7/24.
 */

import com.linkbit.beidou.dao.workOrder.*;
import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2016/7/24.
 */
@Service
public class PortalService extends BaseService {


    @Autowired
    VlineMonthRepository vlineMonthRepository;
    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;

    /**
     * @param reportMonth 月份
     * @param name        工单状态
     * @return 获取线路统计汇总
     */
    public List<VlineMonth> getLineReportNumReportMonth(String reportMonth, String name) {

        return vlineMonthRepository.findByReportMonthAndName(reportMonth, name);
    }

    /**
     * @param offset
     * @return
     */
    public List<Object> findTopNReportByEqClass(int offset) {

        List<Object> dataList = workOrderReportCartRepository.findTopNReportByEqClass(offset);
        return dataList;

    }
}
