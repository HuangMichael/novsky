package com.linkbit.beidou.dao.equipments;

import com.linkbit.beidou.domain.equipments.EqUpdateBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 设备更新申请单
 **/
public interface EqUpdateBillRepository extends PagingAndSortingRepository<EqUpdateBill, Long>, CrudRepository<EqUpdateBill, Long> {


    /**
     * @param pageable
     * @return
     */
    Page<EqUpdateBill> findAll(Pageable pageable);

    /**
     * @return
     */
    List<EqUpdateBill> findAll();


    /**
     * @param id 根据id查询
     * @return
     */
    EqUpdateBill findById(Long id);


    /**
     * @return查询所有的id
     */
    @Query("select id from EqUpdateBill")
    List<Long> findAllIds();
}
