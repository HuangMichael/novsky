package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.workOrder.VworkOrderStep;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 * 维修进度查询
 */
public interface VworkOrderStepRepository extends CrudRepository<VworkOrderStep, Long> {


    /**
     * @param equipment
     * @return 维修进度查询
     */
    List<VworkOrderStep> findByEquipments(Equipments equipment);


    /**
     * @param locations 位置
     * @return 根据位置查询维修流程
     */
    //List<VworkOrderStep> findByLocations(Locations locations);
    @Query(value = "select v from VworkOrderStep v where v.orderLineNo in (select n.orderLineNo from VworkOrderStep n where n.locations =:locations and n.status ='0'  ) order by v.id desc")
    List<VworkOrderStep> findByLocations(@Param("locations") Locations locations);


    /**
     * @param equipmentId 设备ID
     * @return 查询该设备是否在流程中
     */
    @Query("select v from VworkOrderStep v where v.equipments.id =:equipmentId and v.status ='0'")
    Boolean isEquipmentInFlow(@Param("equipmentId") Long equipmentId);

    //查询该位置是否在流程中

    /**
     * @param locationId 位置ID
     * @return 查询该位置是否在流程中
     */
    @Query("select v from VworkOrderStep v where v.locations.id =:locationId and v.status ='0'")
    List<VworkOrderStep> LocationStepsInFlow(@Param("locationId") Long locationId);

    /**
     * @param equipmentsId 位置ID
     * @return 查询该设备是否在流程中
     */
    @Query("select v from VworkOrderStep v where v.equipments.id =:equipmentsId and v.status ='0'")
    List<VworkOrderStep> EquipmentsStepsInFlow(@Param("equipmentsId") Long equipmentsId);


}
