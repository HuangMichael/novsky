package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.WorkOrderHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/5/20.
 * 维修单历史信息
 */
public interface WorkOrderHistoryRepository extends CrudRepository<WorkOrderHistory, Long> {
    /**
     * @param WorkOrderHistory
     * @return
     */
    WorkOrderHistory save(WorkOrderHistory WorkOrderHistory);
    /**
     * @return
     */
    List<WorkOrderHistory> findAll();
    /**
     * @param status
     * @return
     */
    List<WorkOrderHistory> findByStatus(String status);
    /**
     * @param id
     * @return
     */
    WorkOrderHistory findById(Long id);


}
