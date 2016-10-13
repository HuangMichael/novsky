package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.VworkOrderLineNumFixed;
import com.linkbit.beidou.domain.workOrder.VworkOrderLineNumReport;
import com.linkbit.beidou.domain.workOrder.VworkOrderLineNumSuspend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/7/24.
 */
public interface VworkOrderLineNumSuspendRepository extends CrudRepository<VworkOrderLineNumSuspend, Long> {
    List<VworkOrderLineNumSuspend> findAll();


    /**
     * @param reportMonth 根据月份查询
     * @return
     */
    List<VworkOrderLineNumSuspend> findByReportMonth(String reportMonth);

}
