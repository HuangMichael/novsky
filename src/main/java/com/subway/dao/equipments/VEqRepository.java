package com.subway.dao.equipments;


import com.subway.domain.equipments.Vequipments;
import com.subway.domain.locations.Locations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface VEqRepository extends PagingAndSortingRepository<Vequipments, Long>, JpaSpecificationExecutor<Vequipments> {
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
    Page<Vequipments> findByEqNameContainsAndLocNameContainsAndEqClassContains(String eqName, String locName, String eqClass, Pageable pageable);


    /**
     * @param eqCode   设备编号
     * @param eqName   设备名称
     * @param locName  设备位置
     * @param eqClass  设备分类
     * @param pageable 分页属性
     * @return
     */
    Page<Vequipments> findByEqCodeContainsAndEqNameContainsAndLocNameContainsAndEqClassContainsAndLocationContains(String eqCode, String eqName, String locName, String eqClass, String location, Pageable pageable);


    /**
     * @param eqCode  设备编号
     * @param eqName  设备名称
     * @param locName 设备位置
     * @param eqClass 设备分类
     * @return
     */
    List<Vequipments> findByEqCodeContainsAndEqNameContainsAndLocNameContainsAndEqClassContainsAndLocationContains(String eqCode, String eqName, String locName, String eqClass, String location);

}
