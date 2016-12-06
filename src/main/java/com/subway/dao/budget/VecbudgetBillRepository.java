package com.subway.dao.budget;

import com.subway.domain.EcBudget.VEcBudgetBill;
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
public interface VecbudgetBillRepository extends PagingAndSortingRepository<VEcBudgetBill, Long> {


    /**
     * @param pageable
     * @return
     */
    Page<VEcBudgetBill> findAll(Pageable pageable);


    /**
     * @param ecname   根据易耗品名称查询
     * @param pageable 设置可分页
     * @return
     */
    Page<VEcBudgetBill> findByEcnameContains(String ecname, Pageable pageable);


    /**
     * @param ecname 根据易耗品名称查询
     * @return
     */
    List<VEcBudgetBill> findByEcnameContains(String ecname);

    /**
     * @return
     */
    List<VEcBudgetBill> findAll();


    /**
     * @param beginDate
     * @param endDate
     * @param ecname
     * @param location
     * @return
     */
    List<VEcBudgetBill> findByApplyDateBetweenAndEcnameContainsAndLocationContains(String beginDate, String endDate, String ecname, String location);


    /**
     * @param beginDate
     * @param endDate
     * @param ecname
     * @param location
     * @param pageable
     * @return
     */
    Page<VEcBudgetBill> findByApplyDateBetweenAndEcnameContainsAndLocationContains(String beginDate, String endDate, String ecname, String location, Pageable pageable);
}
