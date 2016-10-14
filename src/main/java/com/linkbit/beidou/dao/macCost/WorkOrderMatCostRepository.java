package com.linkbit.beidou.dao.macCost;

import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.matCost.WorkOrderMatCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 工单物料消耗数据接口
 **/
public interface WorkOrderMatCostRepository extends PagingAndSortingRepository<WorkOrderMatCost, Long>, CrudRepository<WorkOrderMatCost, Long> {


    /**
     * @param pageable
     * @return
     */
    Page<WorkOrderMatCost> findAll(Pageable pageable);

    /**
     * @return
     */
    List<WorkOrderMatCost> findAll();


    /**
     * @param
     * @return 批量保存物料消耗数据
     */
    WorkOrderMatCost save(WorkOrderMatCost workOrderMatCost);

    /**
     * @param id 根据id查询
     * @return
     */
    WorkOrderMatCost findById(Long id);


    /**
     * @return查询所有的id
     */
    @Query("select v.id from WorkOrderMatCost v")
    List<Long> findAllIds();


}
