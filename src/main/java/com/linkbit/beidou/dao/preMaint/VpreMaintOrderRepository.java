package com.linkbit.beidou.dao.preMaint;


import com.linkbit.beidou.domain.preMaint.VpreMaintOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 预防性维修视图查询接口
 */
public interface VpreMaintOrderRepository extends PagingAndSortingRepository<VpreMaintOrder, Long> {


    /**
     * @param pageable 分页
     * @return
     */
    Page<VpreMaintOrder> findAll(Pageable pageable);


    /**
     * @param orderDesc
     * @param pageable
     * @return 分页
     */
    Page<VpreMaintOrder> findByOrderDescContaining(String orderDesc, Pageable pageable);
}
