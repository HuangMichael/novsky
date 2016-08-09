package com.linkbit.beidou.service.workOrder;

import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.dao.workOrder.*;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.CommonStatusType;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    WorkOrderReportDetailRepository workOrderReportDetailRepository;
    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;
    @Autowired
    WorkOrderFixDetailRepository workOrderFixDetailRepository;
    @Autowired
    WorkOrderFixRepository workOrderFixRepository;

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


    /**
     * @param array 设备id号数组
     * @return
     */
    public WorkOrderReport generate(Long array[]) {
        WorkOrderReport workOrderReport = new WorkOrderReport();
        workOrderReport.setReportTime(new Date());
        workOrderReport.setOrderNo("WO" + workOrderReport.getReportTime().getTime());
        workOrderReport = workOrderReportRepository.save(workOrderReport);
        for (Long equipmentId : array) {
            WorkOrderReportDetail workOrderReportDetail = new WorkOrderReportDetail();
            Equipments equipments = equipmentsRepository.findById(equipmentId);
            workOrderReportDetail.setEquipments(equipments);
            workOrderReportDetail.setWorkOrderReport(workOrderReport);
            workOrderReportDetailRepository.save(workOrderReportDetail);
        }
        return workOrderReport;
    }


    /**
     * @param workOrderReportDetailList
     * @return 生成保修单
     */
    public WorkOrderReport generateReport1(List<WorkOrderReportDetail> workOrderReportDetailList, String reporter) {
        WorkOrderReport workOrderReport = new WorkOrderReport();
        workOrderReport.setReportTime(new Date());
        workOrderReport.setReporter(reporter);
        workOrderReport.setOrderNo("WO" + workOrderReport.getReportTime().getTime());
        workOrderReport.setStatus("0");
        workOrderReport = workOrderReportRepository.save(workOrderReport);
        for (int i = 1; i < workOrderReportDetailList.size() + 1; i++) {
            WorkOrderReportDetail detail = workOrderReportDetailList.get(i - 1);
            detail.setOrderLineNo(workOrderReport.getOrderNo() + "-" + i);
            detail.setWorkOrderReport(workOrderReport);
            detail.setStatus("0");
            workOrderReportDetailRepository.save(detail);
        }
        return workOrderReport;
    }


    /**
     * @param status
     * @return 根据状态查询所有的报修单
     */
    public List<WorkOrderReport> findByStatus(String status) {

        return workOrderReportRepository.findByStatus(status);
    }


    /**
     * @param status
     * @return 根据状态查询所有的报修单
     */
    public List<WorkOrderReportDetail> findOrderDetailListByStatus(String status) {

        return workOrderReportDetailRepository.findByStatus(status);
    }


    /**
     * @param ids      选中的报修车列表id集合
     * @param reporter 当前登录用户 报修人
     * @return
     */
    public List<WorkOrderReportDetail> generateReport(String ids, String reporter, String location) {
        List<Long> idList = new ArrayList<Long>();
        if (ids != null && !ids.equals("")) {
            idList = StringUtils.str2List(ids, ",");
        }
        List<WorkOrderReportDetail> workOrderReportDetailList = new ArrayList<WorkOrderReportDetail>();

        WorkOrderReportCart workOrderReportCart;
        for (Long id : idList) {
            workOrderReportCart = workOrderReportCartRepository.findById(id);
            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setNodeDesc("已报修");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setStatus("1");
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistoryRepository.save(workOrderHistory);
        }
        return workOrderReportDetailList;
    }


    /**
     * @return 根据类型规约
     * @version 0.1
     */
    public List mapByType(String ids) {
        List<Long> idList = new ArrayList<Long>();
        String array[] = ids.split(",");
        for (String str : array) {
            if (str != null && !str.equals("")) {
                idList.add(Long.parseLong(str));
            }
        }
        return workOrderReportDetailRepository.mapByType(idList);

    }


    /**
     * @return 根据维修单位Id规约
     * @version 0.1
     */
    public List mapByUnitId(String ids) {
        List<Long> idList = new ArrayList<Long>();
        String array[] = ids.split(",");
        for (String str : array) {
            if (str != null && !str.equals("")) {
                idList.add(Long.parseLong(str));
            }
        }
        return workOrderReportDetailRepository.mapByUnitId(idList);

    }


    /**
     * @return 根据所选类型规约
     * @version 0.2
     */
    public List mapBySelectedType(String ids, String selectedType) {
        List<Long> idList = new ArrayList<Long>();
        String array[] = ids.split(",");
        for (String str : array) {
            if (str != null && !str.equals("")) {
                idList.add(Long.parseLong(str));
            }
        }
        if (selectedType.equals("2")) {
            return workOrderReportDetailRepository.mapByEqClass(idList);
        } else {
            return workOrderReportDetailRepository.mapByUnit(idList);
        }
    }


    /**
     * @param list 返回的列表 [uid  did]
     * @return
     */
    public List<WorkOrderFix> createReport(List<Object> list, String personName, String location) {
        log.info(this.getClass().getName() + "----保存维修单");
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        //对于每个维修队 创建一个新的维修单
        WorkOrderFix workOrderFix = null;
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            //创建一个维修单
            workOrderFix = new WorkOrderFix();
            workOrderFix.setReporter(personName);
            workOrderFix.setOrderNo("WF" + new Date().getTime());
            workOrderFix.setStatus("0");
            workOrderFix.setLocation(location);
            workOrderFix.setReportTime(new Date());
            workOrderFix = workOrderFixRepository.save(workOrderFix);
            List<Long> idList = new ArrayList<Long>();
            String ids = (String) object[1];
            if (object[1] != null && !ids.equals("")) {
                idList = StringUtils.str2List(ids, ",");
            }
            for (Long id : idList) {
                WorkOrderReportDetail workOrderReportDetail = workOrderReportDetailRepository.findById(id);
                WorkOrderFixDetail workOrderFixDetail = new WorkOrderFixDetail();
                workOrderFixDetail.setOrderDesc(workOrderReportDetail.getOrderDesc());
                workOrderFixDetail.setEquipmentsClassification(workOrderReportDetail.getEquipmentsClassification());
                workOrderFixDetail.setEquipments(workOrderReportDetail.getEquipments());
                workOrderFixDetail.setStatus(CommonStatusType.FIX_CREATED);
                workOrderFixDetail.setFixDesc("");
                workOrderFixDetail.setUnit(workOrderReportDetail.getUnit());
                workOrderFixDetail.setVlocations(vlocationsRepository.findById(workOrderReportDetail.getLocations().getId()));
                workOrderFixDetail.setOrderLineNo(workOrderReportDetail.getOrderLineNo());
                workOrderFixDetail.setLocations(workOrderReportDetail.getLocations());
                workOrderFixDetail.setWorkOrderFix(workOrderFix);
                workOrderFixDetail.setReportType(workOrderReportDetail.getReportType());
                workOrderFixDetail.setLocation(workOrderReportDetail.getLocation());
                workOrderFixDetail.setReportTime(new Date());
                workOrderFixDetailRepository.save(workOrderFixDetail);
                workOrderReportDetail.setStatus(CommonStatusType.REPORT_COMMITED);//将报修单明细状态修改为已提交
                workOrderReportDetailRepository.save(workOrderReportDetail);
            }
            //根据did列表创建维修单明细
            workOrderFixList.add(workOrderFix);
        }
        return workOrderFixList;
    }


    /**
     * @param list 返回的列表 [uid  did]
     * @return
     */
    public List<WorkOrderFix> preViewFixReport(List<Object> list, String personName, String location) {
        log.info(this.getClass().getName() + "----生成维修单");
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        //对于每个维修队 创建一个新的维修单
        WorkOrderFix workOrderFix = null;
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            //创建一个维修单
            workOrderFix = new WorkOrderFix();
            workOrderFix.setReporter(personName);
            workOrderFix.setOrderNo("WF" + new Date().getTime());
            workOrderFix.setStatus("0");
            workOrderFix.setLocation(location);
            workOrderFix.setReportTime(new Date());
            // workOrderFix = workOrderFixRepository.save(workOrderFix);
            List<Long> idList = new ArrayList<Long>();
            String ids = (String) object[1];
            if (object[1] != null && !ids.equals("")) {
                idList = StringUtils.str2List(ids, ",");
            }
            List<WorkOrderFixDetail> workOrderFixDetailList = new ArrayList<WorkOrderFixDetail>();
            for (Long id : idList) {
                WorkOrderReportDetail workOrderReportDetail = workOrderReportDetailRepository.findById(id);
                WorkOrderFixDetail workOrderFixDetail = new WorkOrderFixDetail();
                workOrderFixDetail.setOrderDesc(workOrderReportDetail.getOrderDesc());
                workOrderFixDetail.setEquipmentsClassification(workOrderReportDetail.getEquipmentsClassification());
                workOrderFixDetail.setEquipments(workOrderReportDetail.getEquipments());
                workOrderFixDetail.setStatus(CommonStatusType.FIX_CREATED);
                workOrderFixDetail.setFixDesc("");
                workOrderFixDetail.setOrderLineNo(workOrderReportDetail.getOrderLineNo());
                workOrderFixDetail.setLocations(workOrderReportDetail.getLocations());
                workOrderFixDetail.setWorkOrderFix(workOrderFix);
                workOrderFixDetail.setReportType(workOrderReportDetail.getReportType());
                workOrderFixDetail.setLocation(workOrderReportDetail.getLocation());
                workOrderFixDetail.setReportTime(new Date());
                workOrderFixDetail.setWorkOrderFix(workOrderFix);
                // workOrderFixDetailRepository.save(workOrderFixDetail);
                workOrderReportDetail.setStatus(CommonStatusType.REPORT_COMMITED);//将报修单明细状态修改为已提交
                // workOrderReportDetailRepository.save(workOrderReportDetail);
                workOrderFixDetailList.add(workOrderFixDetail);
            }

            workOrderFix.setWorkOrderFixDetailList(workOrderFixDetailList);
            //根据did列表创建维修单明细
            workOrderFixList.add(workOrderFix);
        }
        return workOrderFixList;
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
     * @param location 位置编码
     * @return 模糊查询位置编码下对应的报修单信息
     */
    public List<WorkOrderReportDetail> findByLocationStartingWithOrderByReportTimeDesc(String location) {
        List<WorkOrderReportDetail> workOrderReportList = null;
        if (location != null && !location.equals("")) {
            workOrderReportList = workOrderReportDetailRepository.findByLocationStartingWithOrderByReportTimeDesc(location);
        }
        return workOrderReportList;
    }


    /**
     * @param location 位置编码
     * @return 模糊查询位置编码下对应的报修单信息
     */
    public List<WorkOrderReportDetail> findByLocationStartingWithAndStatusOrderByReportTimeDesc(String location, String status) {
        List<WorkOrderReportDetail> workOrderReportList = null;
        if (location != null && !location.equals("")) {
            workOrderReportList = workOrderReportDetailRepository.findByLocationStartingWithAndStatusOrderByReportTimeDesc(location, status);
        }
        return workOrderReportList;
    }


    /**
     * @param equipmentsId 设备id
     * @return 根据设备的id查询设备报修历史信息
     */
    public List<WorkOrderReportDetail> findReportHistoryByEquipmentId(Long equipmentsId) {
        List<WorkOrderReportDetail> workOrderReportDetailList = new ArrayList<WorkOrderReportDetail>();
        if (equipmentsId != null && equipmentsId != 0) {
            Equipments equipments = equipmentsRepository.findById(equipmentsId);
            workOrderReportDetailList = workOrderReportDetailRepository.findByEquipments(equipments);
        }
        return workOrderReportDetailList;
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
     * @param equipmentId
     * @return 根据设备分类获取最后一次维修的跟踪号
     */
    public String getLastOrderLineNoByEquipmentId(Long equipmentId) {
        return workOrderReportDetailRepository.getLastOrderLineNoByEquipmentId(equipmentId);

    }


    public List<Object> getRecortsByPage(Long fromIndex, Long pageCount) {
        return workOrderReportDetailRepository.getRecortsByPage(fromIndex, pageCount);
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
