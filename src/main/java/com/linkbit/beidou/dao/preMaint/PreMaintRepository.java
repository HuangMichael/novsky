package com.linkbit.beidou.dao.preMaint;


import com.linkbit.beidou.domain.premaint.PreMaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface PreMaintRepository extends PagingAndSortingRepository<PreMaint, Long> {


    /**
     * @param pageable 分页
     * @return
     */
    Page<PreMaint> findAll(Pageable pageable);


    /**
     * @param desc
     * @param pageable
     * @return 分页
     */
    Page<PreMaint> findByDescriptionContains(String desc, Pageable pageable);
}
