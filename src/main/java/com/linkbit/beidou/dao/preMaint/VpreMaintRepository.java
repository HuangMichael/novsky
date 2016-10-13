package com.linkbit.beidou.dao.preMaint;


import com.linkbit.beidou.domain.preMaint.VpreMaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 预防性维修视图查询接口
 */
public interface VpreMaintRepository extends PagingAndSortingRepository<VpreMaint, Long> {


    /**
     * @param pageable 分页
     * @return
     */
    Page<VpreMaint> findAll(Pageable pageable);


    /**
     * @param desc
     * @param pageable
     * @return 分页
     */
    Page<VpreMaint> findByPmDescContains(String desc, Pageable pageable);
}