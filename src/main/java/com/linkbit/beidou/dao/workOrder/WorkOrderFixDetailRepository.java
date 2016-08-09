package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.workOrder.WorkOrderFix;
import com.linkbit.beidou.domain.workOrder.WorkOrderFixDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created by huangbin on 2016/5/20.
 */
public interface WorkOrderFixDetailRepository extends CrudRepository<WorkOrderFixDetail, Long> {


    WorkOrderFixDetail save(WorkOrderFixDetail workOrderFixDetail);

    @OrderBy("reportTime desc")
    List<WorkOrderFixDetail> findByStatus(String status);


    @OrderBy("reportTime desc")
    List<WorkOrderFixDetail> findByStatusAndLocationStartingWith(String status, String location);

    WorkOrderFixDetail findById(Long id);

    List<WorkOrderFixDetail> findByWorkOrderFix(WorkOrderFix parent);


    @Query("select  distinct  w.equipmentsClassification   from  WorkOrderFixDetail w where w.id in (:idList) ")
    List<EquipmentsClassification> findEqClassByIds(@Param("idList") List<Long> idList);


    @Query("select  w   from  WorkOrderFixDetail w where w.id in (:idList) ")
    List<EquipmentsClassification> findDetailByIdIn(@Param("idList") List<Long> idList);


    /**
     * @param location 位置编号
     * @return 根据位置编码开头模糊查询和状态查询的维修单集合
     */
    List<WorkOrderFixDetail> findByLocationStartingWithOrderByReportTimeDesc(String location);

}
