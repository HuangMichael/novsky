package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.WorkOrderReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface WorkOrderReportRepository extends CrudRepository<WorkOrderReport, Long> {


    /**
     * @param workOrderReport
     * @return 保存报修单
     */
    WorkOrderReport save(WorkOrderReport workOrderReport);


    /**
     * @return 查询所有报修单
     */
    List<WorkOrderReport> findAll();


    /**
     * @param status
     * @return 根据状态查询报修单
     */
    List<WorkOrderReport> findByStatus(String status);


    /**
     * @param location 位置编码
     * @param status   状态
     * @return
     */
    List<WorkOrderReport> findByLocationStartingWithAndStatus(String location, String status);

}
