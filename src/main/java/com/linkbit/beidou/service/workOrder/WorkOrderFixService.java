package com.linkbit.beidou.service.workOrder;

import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.dao.workOrder.*;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.service.equipments.EquipmentAccountService;
import com.linkbit.beidou.service.locations.LocationsService;
import com.linkbit.beidou.utils.CommonStatusType;
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
}
