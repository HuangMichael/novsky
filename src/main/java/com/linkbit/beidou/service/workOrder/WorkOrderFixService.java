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
    WorkOrderReportRepository workOrderReportRepository;
    @Autowired
    WorkOrderReportDetailRepository workOrderReportDetailRepository;

    @Autowired
    WorkOrderFixRepository workOrderFixRepository;

    @Autowired
    WorkOrderFixDetailRepository workOrderFixDetailRepository;

    @Autowired
    WorkOrderFixFinishRepository workOrderFixFinishRepository;

    @Autowired
    WorkOrderFixSuspendRepository workOrderFixSuspendRepository;

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
     * @param arrayStr
     * @param reporter
     * @return 根据设备分类生成维修单 然后将报修单中的总单和明细信息修改为已报修(status:1)
     */
    public WorkOrderFix generateFix(String arrayStr, String reporter) {
        WorkOrderFix workOrderFix = new WorkOrderFix();
        workOrderFix.setReportTime(new Date());
        workOrderFix.setReporter(reporter);
        workOrderFix.setOrderNo("WF" + workOrderFix.getReportTime().getTime());
        workOrderFix.setStatus("0");
        workOrderFix = workOrderFixRepository.save(workOrderFix);

        String array[] = arrayStr.split(",");
        int i = 1;
        //遍历选择的报修单明细信息
        for (String id : array) {
            WorkOrderReportDetail workOrderReportDetail = workOrderReportDetailRepository.findById(Long.parseLong(id));
            WorkOrderFixDetail workOrderFixDetail = new WorkOrderFixDetail();
            workOrderFixDetail.setEquipmentsClassification(workOrderReportDetail.getEquipmentsClassification());
            workOrderFixDetail.setEquipments(workOrderReportDetail.getEquipments());
            workOrderFixDetail.setMaintainer(workOrderReportDetail.getEquipments().getMaintainer());
            workOrderFixDetail.setOrderDesc(workOrderReportDetail.getOrderDesc());
            workOrderFixDetail.setOrderLineNo(workOrderFix.getOrderNo() + "-" + i);
            workOrderFixDetail.setLocations(workOrderReportDetail.getLocations());
            workOrderFixDetail.setWorkOrderFix(workOrderFix);
            workOrderFixDetail.setStatus(CommonStatusType.ORDER_DISTRIBUTED);
            workOrderFixDetailRepository.save(workOrderFixDetail);
            workOrderReportDetail.setStatus(CommonStatusType.ORDER_DISTRIBUTED);
            workOrderReportDetailRepository.save(workOrderReportDetail);
            WorkOrderReport workOrderReport = workOrderReportDetail.getWorkOrderReport();
            workOrderReport.setStatus(CommonStatusType.ORDER_DISTRIBUTED);
            workOrderReportRepository.save(workOrderReport);
            i++;
        }
        return workOrderFix;
    }

    /**
     * @param idList
     * @return
     */
    public List<EquipmentsClassification> findEqClassByIds(List<Long> idList) {
        List<EquipmentsClassification> equipmentsClassificationList = workOrderFixDetailRepository.findEqClassByIds(idList);
        return equipmentsClassificationList;
    }


    /**
     * @param arrayStr
     * @param reporter
     * @return 批量完成维修单
     */
    public List<WorkOrderFix> finishBatch(String arrayStr, String reporter) {
        List<Long> idList = StringUtils.str2List(arrayStr, reporter);
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        for (Long id : idList) {
            WorkOrderFix workOrderFix = workOrderFixRepository.findById(id);
            workOrderFix.setStatus("1");
            workOrderFix = workOrderFixRepository.save(workOrderFix);
            workOrderFixList.add(workOrderFix);
        }
        return workOrderFixList;
    }

    /**
     * @param arrayStr
     * @param reporter
     * @return 批量完成维修单
     */
    @Transactional
    public List<WorkOrderFixDetail> finishDetailBatch(String arrayStr, String reporter) {
        List<Long> idList = StringUtils.str2List(arrayStr, reporter);
        List<WorkOrderFixDetail> workOrderFixDetailList = new ArrayList<WorkOrderFixDetail>();
        for (Long id : idList) {
            WorkOrderFixDetail workOrderFixDetail = workOrderFixDetailRepository.findById(id);
            workOrderFixDetail.setStatus("1");
            workOrderFixDetail = workOrderFixDetailRepository.save(workOrderFixDetail);
            WorkOrderFixFinish workOrderFixFinish = new WorkOrderFixFinish();
            workOrderFixFinish.setOrderDesc(workOrderFixDetail.getOrderDesc());
            workOrderFixFinish.setLocation(workOrderFixDetail.getLocation());
            workOrderFixFinish.setStatus("1");
            workOrderFixFinish.setUnit(workOrderFixDetail.getUnit());
            workOrderFixFinish.setEquipments(workOrderFixDetail.getEquipments());
            workOrderFixFinish.setEquipmentsClassification(workOrderFixDetail.getEquipmentsClassification());
            workOrderFixFinish.setLocations(workOrderFixDetail.getLocations());
            workOrderFixFinish.setOrderLineNo(workOrderFixDetail.getOrderLineNo());
            workOrderFixFinish.setReportTime(new Date());
            workOrderFixFinish.setReportType(workOrderFixDetail.getReportType());
            workOrderFixFinishRepository.save(workOrderFixFinish);
            workOrderFixDetailList.add(workOrderFixDetail);
            //在完工之前 查看对应的位置或者设备是否还在流程中  所有流程都结束 将该设备或者位置的状态设置为正常(1)
            String reportType = workOrderFixDetail.getReportType();
            if (reportType != null) {
                updateReportSourceStatusAfterFinishing(workOrderFixDetail);
            }
        }
        return workOrderFixDetailList;
    }


    /**
     * @param arrayStr
     * @param
     * @return 批量完成维修单
     */
    @Transactional
    public List<WorkOrderFixDetail> pauseDetailBatch(String arrayStr, String fixDesc) {
        List<Long> idList = StringUtils.str2List(arrayStr, fixDesc);
        List<WorkOrderFixDetail> workOrderFixDetailList = new ArrayList<WorkOrderFixDetail>();
        for (Long id : idList) {
            WorkOrderFixDetail workOrderFixDetail = workOrderFixDetailRepository.findById(id);
            workOrderFixDetail.setStatus("2");
            workOrderFixDetail.setFixDesc(fixDesc);
            workOrderFixDetail = workOrderFixDetailRepository.save(workOrderFixDetail);
            WorkOrderFixSuspend workOrderFixSuspend = new WorkOrderFixSuspend();
            workOrderFixSuspend.setOrderDesc(workOrderFixDetail.getOrderDesc());
            workOrderFixSuspend.setLocation(workOrderFixDetail.getLocation());
            workOrderFixSuspend.setStatus("1");
            workOrderFixSuspend.setUnit(workOrderFixDetail.getUnit());
            workOrderFixSuspend.setEquipments(workOrderFixDetail.getEquipments());
            workOrderFixSuspend.setEquipmentsClassification(workOrderFixDetail.getEquipmentsClassification());
            workOrderFixSuspend.setLocations(workOrderFixDetail.getLocations());
            workOrderFixSuspend.setOrderLineNo(workOrderFixDetail.getOrderLineNo());
            workOrderFixSuspend.setReportTime(new Date());
            workOrderFixSuspend.setFixDesc(fixDesc);
            workOrderFixSuspend.setVlocations(vlocationsRepository.findById(workOrderFixDetail.getLocations().getId()));
            workOrderFixSuspend.setReportType(workOrderFixDetail.getReportType());
            workOrderFixSuspendRepository.save(workOrderFixSuspend);
            workOrderFixDetailList.add(workOrderFixDetail);
        }
        return workOrderFixDetailList;
    }


    /**
     * @param arrayStr
     * @param reporter
     * @return 批量完成维修单
     */
    public List<WorkOrderFix> pauseBatch(String arrayStr, String reporter) {
        List<Long> idList = StringUtils.str2List(arrayStr, reporter);
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        for (Long id : idList) {
            WorkOrderFix workOrderFix = workOrderFixRepository.findById(id);
            workOrderFix.setStatus("2");
            workOrderFix = workOrderFixRepository.save(workOrderFix);
            workOrderFixList.add(workOrderFix);
        }
        return workOrderFixList;
    }

    /**
     * @param id
     * @return 批量完成维修单
     */
    @Transactional
    public WorkOrderFix transform(Long id) {
        WorkOrderFixDetail oldObj = workOrderFixDetailRepository.findById(id);
        WorkOrderFix oldFix = oldObj.getWorkOrderFix();
        WorkOrderFix workOrderFix = new WorkOrderFix();
        workOrderFix.setOrderDesc(oldFix.getOrderDesc());
        workOrderFix.setStatus("0");
        workOrderFix.setReportTime(oldFix.getReportTime());
        workOrderFix.setOrderNo(oldFix.getOrderNo() + "-Z");
        workOrderFix.setReporter(oldFix.getReporter());
        workOrderFixRepository.save(workOrderFix);
        WorkOrderFixDetail newObj = new WorkOrderFixDetail();
        newObj.setMaintainer(oldObj.getMaintainer());
        newObj.setStatus("0");
        newObj.setWorkOrderFix(workOrderFix);
        newObj.setLocations(oldObj.getLocations());
        newObj.setEquipments(oldObj.getEquipments());
        newObj.setEquipmentsClassification(oldObj.getEquipmentsClassification());
        newObj.setOrderLineNo(workOrderFix.getOrderNo() + "-1");
        newObj.setOrderDesc(oldObj.getOrderDesc());
        workOrderFixDetailRepository.save(newObj);
        return workOrderFix;
    }


    /**
     * @return 批量完成维修单
     */
    public List<WorkOrderFix> findAll() {

        List<WorkOrderFix> workOrderFixList = workOrderFixRepository.findAll();

        return workOrderFixList;
    }


    /**
     * @param location
     * @return 根据位置编号查询已提交完成的工单信息
     */
    public List<WorkOrderFix> findFinishedOrders(String location) {
        return workOrderFixRepository.findByLocationStartWithAndStatusLessThan(location, "1");
    }


    /**
     * @param workOrderFixDetail 维修明细
     *                           维修完成后更新设备或者状态信息为正常
     */
    public void updateReportSourceStatusAfterFinishing(WorkOrderFixDetail workOrderFixDetail) {
        boolean repaired;
        String type = workOrderFixDetail.getReportType();

        if (type == null || type.equals("")) {
            log.error("报修类型为空");
            return;
        } else if (type.equals("w")) {
            //查询位置是否在报修流程中
            Locations locations = workOrderFixDetail.getLocations();
            repaired = locationsService.isLocationOutOfFlow(locations.getId());
            if (repaired) {
                //将locationId对应的位置状态修改为正常1
                locations.setStatus(CommonStatusType.LOC_NORMAL);
                locationsService.save(locations);
            }

        } else if (type.equals("s")) {
            //查询设备是否在报修流程中
            Equipments equipments = workOrderFixDetail.getEquipments();
            repaired = equipmentAccountService.isEquipmentsOutOfFlow(equipments.getId());
            if (repaired) {
                //将locationId对应的位置状态修改为正常1
                equipments.setStatus(CommonStatusType.EQ_NORMAL);
                equipmentAccountService.save(equipments);
            }
        }
    }


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
