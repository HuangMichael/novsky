package com.linkbit.beidou.service.workOrder;

import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportCartRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportDetailRepository;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportDetail;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.CommonStatusType;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin  on 2016/5/20.
 * 工单调度信息业务类
 */
@Service
public class WorkOrderDispatchService extends BaseService {

    @Autowired
    WorkOrderReportDetailRepository workOrderReportDetailRepository;
    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;

    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;

    /**
     * @return 查询 报修单明细信息
     */
    public List<WorkOrderReportDetail> findReportDetailList() {

        return workOrderReportDetailRepository.findByStatus(CommonStatusType.ORDER_CREATED);

    }

    /**
     * @param location
     * @param status
     * @return 查询 报修单明细信息
     */
    public List<WorkOrderReportDetail> findReportList(String location, String status) {

        return workOrderReportDetailRepository.findByLocationStartingWithAndStatusLessThan(location, status);

    }


    /**
     * @param ids
     * @return 根据id查询维修车信息集合
     */
    public List<WorkOrderReportDetail> findWorkOrderReportDetailByIds(String ids) {

        String array[] = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String a : array) {
            list.add(Long.parseLong(a));
        }
        return workOrderReportDetailRepository.findWorkOrderReportDetailByIds(list);
    }

    /**
     * @param detailId
     * @param unitId
     * @return 返回更新了维修单位的报修明细信息
     */
    public WorkOrderReportCart updateDetailUnit(Long detailId, Long unitId) {
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(detailId);
        OutsourcingUnit outsourcingUnit = outsourcingUnitRepository.findById(unitId);
        if (workOrderReportCart != null && outsourcingUnit != null) {
            workOrderReportCart.setUnit(outsourcingUnit);
            workOrderReportCart.setNodeState("已派工");
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);
        }
        return workOrderReportCart;
    }


    /**
     * @param ids
     * @return 根据id查询维修车信息集合
     */
    public List<WorkOrderReportCart> findWorkOrderReportCartByIds(String ids) {
        List<WorkOrderReportCart> workOrderReportCartList = new ArrayList<WorkOrderReportCart>();
        List<Long> longList = StringUtils.str2List(ids, ",");
        for (int i = 0; i < longList.size(); i++) {
            WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(longList.get(i));
            workOrderReportCartList.add(workOrderReportCart);
        }
        return workOrderReportCartList;
    }
}
