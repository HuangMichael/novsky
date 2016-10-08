package com.linkbit.beidou.dao.equipments;


import com.linkbit.beidou.domain.equipments.Vequipments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface VEqRepository extends PagingAndSortingRepository<Vequipments, Long>, JpaSpecificationExecutor<Vequipments> {


    /**
     * @param eqName   设备名称
     * @param pageable 根据设备名称查询
     * @return
     */
    Page<Vequipments> findByEqNameContains(String eqName, Pageable pageable);

    /**
     * @param pageable
     * @return
     */
    Page<Vequipments> findAll(Pageable pageable);

    /**
     * @param eqName   设备名称
     * @param locName  设备位置
     * @param eqClass  设备分类
     * @param pageable 分页属性
     * @return
     */
    @Query
    Page<Vequipments> findByEqNameContainsAndLocNameContainsAndEqClassContains(String eqName, String locName, String eqClass, Pageable pageable);

}
