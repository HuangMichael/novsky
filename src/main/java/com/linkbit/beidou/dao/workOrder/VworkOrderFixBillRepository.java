package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.VworkOrderFixBill;
import com.linkbit.beidou.domain.workOrder.VworkOrderReportBill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/9/2.
 */
public interface VworkOrderFixBillRepository extends PagingAndSortingRepository<VworkOrderFixBill, Long>, JpaSpecificationExecutor<VworkOrderFixBill> {

    List<VworkOrderFixBill> findAll();


    /**
     * @param nodeState
     * @return 节点状态
     */
    List<VworkOrderFixBill> findByNodeStateOrderByExpiredHoursDesc(String nodeState);


}
