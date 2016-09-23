package com.linkbit.beidou.dao.equipments;

import com.linkbit.beidou.domain.equipments.EqUpdateBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 设备更新申请单数据接口
 *
 * @Date 2016年9月23日10:46:47
 **/
public interface EqUpdateBillRepository extends PagingAndSortingRepository<EqUpdateBill, Long>, CrudRepository<EqUpdateBill, Long> {

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
