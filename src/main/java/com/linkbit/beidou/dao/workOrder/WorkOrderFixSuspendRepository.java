package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.WorkOrderFixSuspend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/5/20.
 */
public interface WorkOrderFixSuspendRepository extends CrudRepository<WorkOrderFixSuspend, Long> {


    WorkOrderFixSuspend save(WorkOrderFixSuspend workOrderFixSuspend);

    List<WorkOrderFixSuspend> findByStatus(String status);

    WorkOrderFixSuspend findById(Long id);


}
