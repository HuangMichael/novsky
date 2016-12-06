package com.subway.service.workOrder;

import com.subway.dao.equipments.EquipmentsClassificationRepository;
import com.subway.dao.equipments.EquipmentsRepository;
import com.subway.dao.locations.VlocationsRepository;
import com.subway.dao.workOrder.VworkOrderFixBillRepository;
import com.subway.dao.workOrder.WorkOrderHistoryRepository;
import com.subway.dao.workOrder.WorkOrderReportCartRepository;
import com.subway.domain.equipments.EquipmentsClassification;
import com.subway.domain.workOrder.WorkOrderHistory;
import com.subway.domain.workOrder.WorkOrderReportCart;
import com.subway.service.app.BaseService;
import com.subway.service.equipments.EquipmentAccountService;
import com.subway.service.locations.LocationsService;
import com.subway.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    VworkOrderFixBillRepository vworkOrderFixBillRepository;



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

    /**
     * @param workOrderReportCart 工单信息
     * @param fixDesc             维修描述
     * @param status              工单状态
     * @return
     */
    @Transactional
    public WorkOrderHistory handleWorkOrder(WorkOrderReportCart workOrderReportCart, String fixDesc, String status) {
        WorkOrderHistory workOrderHistory = null;
        if (!workOrderReportCart.getNodeState().equals(status)) {
            workOrderReportCart.setStatus("1");
            workOrderReportCart.setNodeState(status);
            workOrderReportCart.setFixDesc(fixDesc);
            workOrderReportCart.setLastStatusTime(new Date());
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);
            updateNodeStatus(workOrderReportCart);
            //插入一条最新状态记录
            workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistory.setStatus("1");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setNodeDesc(status);
            workOrderHistory = workOrderHistoryRepository.save(workOrderHistory);

        }
        return workOrderHistory;
    }


    public Long findExpired() {

        return vworkOrderFixBillRepository.findExpired();
    }
}
