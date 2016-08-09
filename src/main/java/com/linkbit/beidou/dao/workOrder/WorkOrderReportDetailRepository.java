package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.workOrder.WorkOrderReport;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by huangbin on 2016/5/20.
 */
public interface WorkOrderReportDetailRepository extends CrudRepository<WorkOrderReportDetail, Long> {

    /**
     * @param workOrderReportDetail
     * @return 保存报修单明细信息
     */
    WorkOrderReportDetail save(WorkOrderReportDetail workOrderReportDetail);

    /**
     * @param status
     * @return 根据状态查询明细信息
     */
    List<WorkOrderReportDetail> findByStatus(String status);

    /**
     * @param id
     * @return 根据id查询报修单明细信息
     */
    WorkOrderReportDetail findById(Long id);


    /**
     * 根据分类字段进行规约报修明细单
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT  e.equipments_classification_id as uid,GROUP_CONCAT(d.id) AS did FROM t_work_order_report_detail d LEFT JOIN t_equipments e ON d.equipments_id = e.id WHERE  e.status = '0' AND d.status = '0' and d.id in :ids GROUP BY e.equipments_classification_id")
    List mapByEqClass(@Param("ids") List<Long> ids);


    /**
     * 根据分类字段进行规约报修明细单
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT  e.maintain_unit_id as uid,GROUP_CONCAT(d.id) AS did FROM t_work_order_report_detail d LEFT JOIN t_equipments e ON d.equipments_id = e.id WHERE  e.status = '0' AND d.status = '0' and d.id in :ids GROUP BY e.maintain_unit_id")
    List mapByUnit(@Param("ids") List<Long> ids);


    /**
     * 根据分类字段进行规约报修明细单
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT  e.maintain_unit_id as uid,GROUP_CONCAT(d.id) AS did FROM t_work_order_report_detail d LEFT JOIN t_equipments e ON d.equipments_id = e.id WHERE  e.status = '0' AND d.status = '0' and d.id in :ids GROUP BY e.maintain_unit_id")
    List mapByType(@Param("ids") List<Long> ids);


    /**
     * 根据分类字段进行规约报修明细单
     *
     * @param selectedType 根据选择的类型规约
     * @param ids          id列表
     * @param groupType    分组依据
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT  :selectedType as uid,GROUP_CONCAT(d.id) AS did FROM t_work_order_report_detail d LEFT JOIN t_equipments e ON d.equipments_id = e.id WHERE  e.status = '0' AND d.status = '0' and d.id in :ids GROUP BY :groupType")
    List mapBySelectType(@Param("selectedType") String selectedType, @Param("ids") List<Long> ids, @Param("groupType") String groupType);


    /**
     * @param location 位置编码
     * @param status   报修单状态
     * @return 根据状态查询明细信息
     */
    List<WorkOrderReportDetail> findByLocationStartingWithAndStatusLessThan(String location, String status);


    /**
     * @param equipments 根据设备查询报修信息
     * @return
     */
    List<WorkOrderReportDetail> findByEquipments(Equipments equipments);


    /**
     * @param ids 选择的维修明细的ID的集合
     * @return 根据维修单位进行规约
     */
    @Query(nativeQuery = true, value = "SELECT d.unit_id AS uid, GROUP_CONCAT(d.id) AS did FROM t_work_order_report_detail d where d.status =0 and  d.id in :ids GROUP BY d.unit_id")
    List mapByUnitId(@Param("ids") List<Long> ids);


    @Query(nativeQuery = true, value = "select c.order_line_no from t_work_order_report_cart c where c.equipments_id =:eid order by c.report_time desc limit 1")
    String getLastOrderLineNoByEquipmentId(@Param("eid") Long eid);


    @Query("SELECT r from WorkOrderReportDetail r where r.id in :ids ")
    List<WorkOrderReportDetail> findWorkOrderReportDetailByIds(@Param("ids") List<Long> ids);


    @Query(nativeQuery = true, value = "SELECT  r.id,e.eq_code,e.description,r.order_line_no, r.order_desc,DATE_FORMAT(r.report_time,'%Y-%m-%d %T') report_time ,c.description eqclass,r.status FROM t_work_order_report_detail r LEFT JOIN t_equipments e  on r.equipments_id =e.id LEFT JOIN  t_equipments_classification c ON r.eq_class_id = c.id where 1=1  limit :fromIndex ,:perPageCount ")
    List<Object> getRecortsByPage(@Param("fromIndex") Long fromIndex, @Param("perPageCount") Long perPageCount);


    /**
     * @param location 位置编码
     * @param status   状态
     * @return
     */
    List<WorkOrderReportDetail> findByLocationStartingWithAndStatusOrderByReportTimeDesc(String location, String status);

    /**
     * @param location 位置编码
     * @return
     */
    List<WorkOrderReportDetail> findByLocationStartingWithOrderByReportTimeDesc(String location);

}
