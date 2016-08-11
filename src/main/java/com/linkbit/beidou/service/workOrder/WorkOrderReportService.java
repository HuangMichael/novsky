package com.linkbit.beidou.service.workOrder;

import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.dao.workOrder.*;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin  on 2016/5/20.
 */
@Service
public class WorkOrderReportService extends BaseService {


    @Autowired
    EquipmentsRepository equipmentsRepository;

    @Autowired
    WorkOrderReportRepository workOrderReportRepository;

    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;

    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;
    @Autowired
    VlocationsRepository vlocationsRepository;
    @Autowired
    VworkOrderNumReportRepository vworkOrderNumReportRepository;

    @Autowired
    VworkOrderNumFinishRepository vworkOrderNumFinishRepository;

    @Autowired
    WorkOrderHistoryRepository workOrderHistoryRepository;

    @Autowired
    WorkOrderFixService workOrderFixService;


    /**
     * @param status
     * @return 根据状态查询所有的报修单
     */
    public List<WorkOrderReport> findByStatus(String status) {

        return workOrderReportRepository.findByStatus(status);
    }


    /**
     * @param ids 选中的报修车列表id集合
     * @return
     */
    @Transactional
    public List<WorkOrderReportCart> generateReport(String ids) {
        List<Long> idList = new ArrayList<Long>();
        if (ids != null && !ids.equals("")) {
            idList = StringUtils.str2List(ids, ",");
        }
        List<WorkOrderReportCart> workOrderReportCartList = new ArrayList<WorkOrderReportCart>();
        WorkOrderReportCart workOrderReportCart;
        for (Long id : idList) {
            workOrderReportCart = workOrderReportCartRepository.findById(id);
            workOrderReportCart.setNodeState("已报修");
            workOrderReportCartRepository.save(workOrderReportCart);
            workOrderFixService.updateNodeStatus(workOrderReportCart);
            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setNodeDesc("已报修");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setStatus("1");
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistoryRepository.save(workOrderHistory);
        }
        return workOrderReportCartList;
    }


    /**
     * @return 根据维修单位Id规约
     * @version 0.1
     */
    public List mapByUnitId(String ids) {
        List<WorkOrderReportCart> workOrderReportCartList = new ArrayList<WorkOrderReportCart>();
        List<Long> idsList = StringUtils.str2List(ids, ",");
        for (Long id : idsList) {
            WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(id);
            workOrderReportCart.setNodeState("已派工");
            workOrderReportCartRepository.save(workOrderReportCart);
            workOrderFixService.updateNodeStatus(workOrderReportCart);
            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setNodeDesc("已派工");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setStatus("1");
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistoryRepository.save(workOrderHistory);
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);
            workOrderReportCartList.add(workOrderReportCart);
        }
        return workOrderReportCartList;

    }


    /**
     * @param location 位置编码
     * @param status   状态
     * @return 模糊查询位置编码下对应的报修单信息
     */
    public List<WorkOrderReport> findByLocationStartingWithAndStatus(String location, String status) {
        List<WorkOrderReport> workOrderReportList = null;
        if (location != null && !location.equals("") && status != null && !status.equals("")) {
            workOrderReportList = workOrderReportRepository.findByLocationStartingWithAndStatus(location, status);
        }
        return workOrderReportList;
    }


    /**
     * @param equipmentsClassification
     * @return 根据设备分类信息获取维修单位
     */
    public OutsourcingUnit getDefaultUnitByEqClass(EquipmentsClassification equipmentsClassification) {
        List<OutsourcingUnit> unitList = equipmentsClassification.getUnitSet();
        if (!unitList.isEmpty()) {
            return unitList.get(0);
        } else {
            return outsourcingUnitRepository.findAll().get(0);
        }
    }


    /**
     * @return 查询近期三个月的报修单数量
     */
    //  * @param location 查询当前用户的位置下的报修数量统计
    public List<VworkOrderNumReport> selectReportNumIn3Months() {
        return vworkOrderNumReportRepository.findAll();
    }

    /**
     * @return 查询近期三个月的报修单数量
     */
    //  * @param location 查询当前用户的位置下的报修数量统计
    public List<VworkOrderNumFinish> selectFinishNumIn3Months() {
        return vworkOrderNumFinishRepository.findAll();
    }


}
