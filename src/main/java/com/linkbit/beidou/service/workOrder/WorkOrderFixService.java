package com.linkbit.beidou.service.workOrder;

import com.linkbit.beidou.dao.equipments.EquipmentsClassificationRepository;
import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderHistoryRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportCartRepository;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.workOrder.WorkOrderHistory;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.service.equipments.EquipmentAccountService;
import com.linkbit.beidou.service.locations.LocationsService;
import com.linkbit.beidou.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin  on 2016/5/20.
 */
@Service
public class WorkOrderFixService extends BaseService {
    @Autowired
    EquipmentsRepository equipmentsRepository;


    @Autowired
    LocationsService locationsService;

    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;

    @Autowired
    EquipmentAccountService equipmentAccountService;

    @Autowired
    VlocationsRepository vlocationsRepository;

    @Autowired
    WorkOrderHistoryRepository workOrderHistoryRepository;

    @Autowired
    EquipmentsClassificationRepository equipmentsClassificationRepository;

    /**
     * @return 查询已派工的维修单
     */
    public List<WorkOrderReportCart> findDistributedOrders(String location) {
        return workOrderReportCartRepository.findByLocationStartingWithAndNodeState(location, "已派工");
    }

    /**
     * @return 查询已完工的维修单
     */
    public List<WorkOrderReportCart> findFinishOrders(String location) {
        return workOrderReportCartRepository.findByLocationStartingWithAndNodeState(location, "已完工");
    }

    /**
     * @return 查询已暂停的维修单
     */
    public List<WorkOrderReportCart> findPausedOrders(String location) {
        return workOrderReportCartRepository.findByLocationStartingWithAndNodeState(location, "已暂停");
    }

    /**
     * @return 查询已取消的维修单
     */
    public List<WorkOrderReportCart> findRemovedOrders(String location) {
        return workOrderReportCartRepository.findByLocationStartingWithAndNodeState(location, "已取消");
    }


    /**
     * @param workOrderReportCart
     */
    public void updateNodeStatus(WorkOrderReportCart workOrderReportCart) {
        if (workOrderReportCart != null) {
            List<WorkOrderHistory> workOrderHistoryList = workOrderHistoryRepository.findByWorkOrderReportCart(workOrderReportCart);
            for (WorkOrderHistory workOrderHistory : workOrderHistoryList) {
                workOrderHistory.setStatus("0");
                workOrderHistoryRepository.save(workOrderHistory);
            }

        }
    }

    /**
     * @param orderId     维修单id
     * @param deadLineStr
     * @return
     */
    public WorkOrderReportCart updateDeadLine(Long orderId, String deadLineStr) {
        Date deadLine = null;
        try {
            deadLine = DateUtils.convertStr2Date(deadLineStr, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(orderId);
        workOrderReportCart.setDeadLine(deadLine);
        return workOrderReportCartRepository.save(workOrderReportCart);
    }


    //根据设备分类设置维修期限


    /**
     * @param workOrderReportCart
     * @return 根据设备分类查询维修时限 返回小时
     */
    public Date getDeadLineByEqClass(WorkOrderReportCart workOrderReportCart) {
        Long limitHours = 0l;
        EquipmentsClassification equipmentsClassification = null;
        Date deadLine = null;
        if (workOrderReportCart != null) {
            equipmentsClassification = workOrderReportCart.getEquipmentsClassification();
            limitHours = equipmentsClassification.getLimitHours();
            Date reportDate = DateUtils.getCellingDate(workOrderReportCart.getReportTime());
            deadLine = DateUtils.addDate(reportDate, Calendar.HOUR, limitHours.intValue());
        }
        return deadLine;
    }
}
