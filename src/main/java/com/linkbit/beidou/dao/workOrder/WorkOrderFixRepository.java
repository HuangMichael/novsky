package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.WorkOrderFix;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface WorkOrderFixRepository extends CrudRepository<WorkOrderFix, Long> {


    /**
     * @param WorkOrderFix
     * @return
     */
    WorkOrderFix save(WorkOrderFix WorkOrderFix);


    /**
     * @return
     */
    List<WorkOrderFix> findAll();


    /**
     * @param status
     * @return
     */
    List<WorkOrderFix> findByStatus(String status);


    /**
     * @param id
     * @return
     */
    WorkOrderFix findById(Long id);


    /**
     * @param location 位置编号
     * @param status   维修单状态
     * @return
     */
    @Query("select w from WorkOrderFix w where w.location like :location and w.status <> :status")
    List<WorkOrderFix> findByLocationStartWithAndStatusLessThan(@Param("location") String location, @Param("status") String status);



    /**
     * @param location 位置编号
     * @param status   维修单状态
     * @return
     */
    @Query("select w from WorkOrderFix w where w.location like :location and w.status =:status")
    List<WorkOrderFix> findByLocationStartWithAndStatusEquals(@Param("location") String location, @Param("status") String status);


}
