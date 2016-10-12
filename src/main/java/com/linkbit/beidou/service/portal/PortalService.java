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
    public List<VworkOrderLineNumReport> getLineReportNum() {

        return vworkOrderLineNumReportRepository.findAll();
    }


    /**
     * @return 获取线路总完工数量
     */
    public List<VworkOrderLineNumFixed> getLineFixedNum() {

        return vworkOrderLineNumFixedRepository.findAll();
    }


    /**
     * @return 获取线路总维修中数量
     */
    public List<VworkOrderLineNumFixing> getLineFixingNum() {

        return vworkOrderLineNumFixingRepository.findAll();
    }

    /**
     * @return 获取线路总维修中数量
     */
    public List<VworkOrderLineNumAbort> getLineAbortNum() {


        return vworkOrderLineNumAbortRepository.findAll();
    }


    /**
     * @return 获取线路总暂停数量
     */
    public List<VworkOrderLineNumSuspend> getLineSuspendNum() {

        return vworkOrderLineNumSuspendRepository.findAll();
    }


    /**
     * @param offset
     * @return
     */
    public List<Object> findTopNReportByEqClass(int offset) {

        List<Object> dataList = workOrderReportCartRepository.findTopNReportByEqClass(offset);
        return dataList;

    }


    /**
     * @param dataList
     * @return
     */
    public List<Object> assembleData(List<Object> dataList) {
        List<Object> data = null;
        int sumCount = 0;
        for (int i = 5; i < dataList.size(); i++) {
            Map map = (HashMap) dataList.get(i);
            System.out.println("object-------" + map.get("0"));
            System.out.println("object-------" + map.get("1"));
            System.out.println("object-------" + map.get("2"));
            System.out.println("----------------------------");
        }
        return data;
    }

}
