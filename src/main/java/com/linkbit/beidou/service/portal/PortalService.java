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
    VworkOrderLineNumReportRepository vworkOrderLineNumReportRepository;
    @Autowired
    VworkOrderLineNumFixedRepository vworkOrderLineNumFixedRepository;
    @Autowired
    VworkOrderLineNumFixingRepository vworkOrderLineNumFixingRepository;
    @Autowired
    VworkOrderLineNumSuspendRepository vworkOrderLineNumSuspendRepository;
    @Autowired
    VworkOrderLineNumAbortRepository vworkOrderLineNumAbortRepository;
    @Autowired
    WorkOrderReportCartRepository
            workOrderReportCartRepository;


    /**
     * @return 获取线路总报修数量
     */
    public List<VworkOrderLineNumReport> getLineReportNumReportMonth(String reportMonth) {

        return vworkOrderLineNumReportRepository.findByReportMonth(reportMonth);
    }

    /**
     * @param reportMonth
     * @return 获取线路总完工数量
     */
    public List<VworkOrderLineNumFixed> getLineFixedNumByReportMonth(String reportMonth) {

        return vworkOrderLineNumFixedRepository.findByReportMonth(reportMonth);
    }


    /**
     * @return 获取线路总维修中数量
     */
    public List<VworkOrderLineNumFixing> getLineFixingNumByReportMonth(String reportMonth) {

        return vworkOrderLineNumFixingRepository.findByReportMonth(reportMonth);
    }

    /**
     * @return 获取线路总维修中数量
     */
    public List<VworkOrderLineNumAbort> getLineAbortNumReportMonth(String reportMonth) {


        return vworkOrderLineNumAbortRepository.findByReportMonth(reportMonth);
    }


    /**
     * @return 获取线路总暂停数量
     */
    public List<VworkOrderLineNumSuspend> getLineSuspendNumByReportMonth(String reportMonth) {

        return vworkOrderLineNumSuspendRepository.findByReportMonth(reportMonth);
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
