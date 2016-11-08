package com.linkbit.beidou.dao.outsourcingUnit;


import com.linkbit.beidou.domain.units.Units;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 外委单位信息查询接口
 */
public interface OutsourcingUnitRepository extends CrudRepository<Units, Long>, PagingAndSortingRepository<Units, Long> {
    /**
     * 查询所有外委单位信息
     */
    List<Units> findAll();


    /**
     * @param unitCode
     * @return 根据单位编号查询
     */
    List<Units> findByUnitNo(String unitCode);


    /**
     * @return 根据单位编号查询
     */
    Units save(Units outsourcingUnit);


    /**
     * 根据状态查询外委单位
     */
    List<Units> findByStatus(String status);

    /**
     * 根据id查询
     */
    Units findById(long id);

    /**
     * @param eqClassId 设备分类ID
     * @return 根据设备分类查询对应的外委单位信息 id 描述
     */
    @Query(nativeQuery = true, value = "SELECT u.id, u.description, u.linkman, u.telephone  FROM t_outsourcing_unit u WHERE u.id  IN (SELECT uc.unit_id  FROM t_unit_class uc WHERE uc.class_id = :eqClassId) AND u.status = '1'  AND u.description <> '无'")
    List<Object> findUnitListByEqClassIdEq(@Param("eqClassId") Long eqClassId);


    /**
     * @param idList id 集合
     * @return 根据设备分类查询非该分类对应的外委单位信息 id 描述
     */
    @Query(nativeQuery = true, value = "SELECT u.id, u.description, u.linkman, u.telephone  FROM t_outsourcing_unit u WHERE u.id NOT IN (:idList) AND u.status = '1'  AND u.description <> '无'")
    List<Object> findUnitListByEqClassIdNotEq(@Param("idList") List<Long> idList);


    /**
     * @param unitName
     * @return 单位集合
     */
    List<Units> findByDescriptionContains(String unitName);


    /**
     * @param unitName 外委单位 名称
     * @param pageable 分页
     * @return 单位集合
     */
    Page<Units> findByDescriptionContains(String unitName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select u.id from Units u")
    List<Long> findAllIds();

}
