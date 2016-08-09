package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.WorkOrderFixDetail;
import com.linkbit.beidou.domain.workOrder.WorkOrderFixFinish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/5/20.
 */
public interface WorkOrderFixFinishRepository extends CrudRepository<WorkOrderFixFinish, Long> {


    WorkOrderFixFinish save(WorkOrderFixFinish workOrderFixFinish);

    List<WorkOrderFixFinish> findByStatus(String status);

    WorkOrderFixFinish findById(Long id);


}
