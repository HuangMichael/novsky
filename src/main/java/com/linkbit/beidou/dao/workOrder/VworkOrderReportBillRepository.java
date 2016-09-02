package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.VworkOrderReportBill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/9/2.
 */
public interface VworkOrderReportBillRepository extends PagingAndSortingRepository<VworkOrderReportBill, Long>, JpaSpecificationExecutor<VworkOrderReportBill> {

    List<VworkOrderReportBill> findAll();

    @Query("SELECT count(r) from VworkOrderReportBill r  ")
    Long selectCount();

}
