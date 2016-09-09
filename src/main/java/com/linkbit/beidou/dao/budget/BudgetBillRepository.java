package com.linkbit.beidou.dao.budget;

import com.linkbit.beidou.domain.budget.BudgetBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 采购申请单
 *
 * @author
 * @create 2016-09-09 11:16
 **/
public interface BudgetBillRepository extends PagingAndSortingRepository<BudgetBill, Long> {


    /**
     * @param pageable
     * @return
     */
    Page<BudgetBill> findAll(Pageable pageable);

    /**
     * @return
     */
    List<BudgetBill> findAll();
}
