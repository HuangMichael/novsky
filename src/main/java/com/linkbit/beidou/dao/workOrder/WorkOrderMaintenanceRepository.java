package com.linkbit.beidou.dao.workOrder;


import com.linkbit.beidou.domain.workOrder.WorkOrderMaintenance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/1/8 0008.
 */
public interface WorkOrderMaintenanceRepository extends CrudRepository<WorkOrderMaintenance, Long> {
    /**
     * 查询所有菜单
     */
    List<WorkOrderMaintenance> findAll();
    /**
     * 根据id查询
     */
    WorkOrderMaintenance findById(long id);

    /**
     * 保存工单信息
     */
    WorkOrderMaintenance save(WorkOrderMaintenance workOrderMaintenance);


}
