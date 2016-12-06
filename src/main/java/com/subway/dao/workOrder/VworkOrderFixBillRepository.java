package com.subway.dao.workOrder;

import com.subway.domain.workOrder.VworkOrderFixBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created by huangbin on 2016/9/2.
 */
public interface VworkOrderFixBillRepository extends PagingAndSortingRepository<VworkOrderFixBill, Long>, JpaSpecificationExecutor<VworkOrderFixBill> {

    List<VworkOrderFixBill> findAll();

    /**
     * @param location  位置编号
     * @param nodeState 节点状态
     * @return 根据用户位置和节点状态查询
     */
    @OrderBy("nodeTime desc,dealLine desc,id desc")
    List<VworkOrderFixBill> findByLocationStartingWithAndNodeState(String location, String nodeState);

    /**
     * @param location  位置编号
     * @param nodeState 节点状态
     * @param orderDesc
     * @param pageable  可分页
     * @return
     */
    Page<VworkOrderFixBill> findByLocationStartingWithAndNodeStateAndOrderDescContainingOrderByNodeTimeDesc(String location, String nodeState, String orderDesc, Pageable pageable);


    /**
     * @param location  位置编号
     * @param nodeState 节点状态
     * @param orderDesc 不分页
     * @return
     */
    List<VworkOrderFixBill> findByLocationStartingWithAndNodeStateAndOrderDescContainingOrderByNodeTimeDesc(String location, String nodeState, String orderDesc);


    /**
     * @param orderLineNo
     * @param orderDesc   报修描述
     * @param location
     * @param eqClass
     * @param pageable
     * @return 模糊查询
     */
    Page<VworkOrderFixBill> findByNodeStateContainsAndOrderLineNoContainsAndOrderDescContainsAndLocNameContainsAndEqClassContainsAndExpiredContains(String nodeStatus, String orderLineNo, String orderDesc, String location, String eqClass, String expired, Pageable pageable);


    /**
     * @param orderLineNo
     * @param orderDesc   报修描述
     * @param location
     * @param eqClass
     * @return 模糊查询
     */
    List<VworkOrderFixBill> findByNodeStateContainsAndOrderLineNoContainsAndOrderDescContainsAndLocNameContainsAndEqClassContainsAndExpiredContains(String nodeStatus, String orderLineNo, String orderDesc, String location, String eqClass, String expired);


    /**
     * @return 统计已派工  已超期的工单数量
     */
    @Query(value = "select count(v)  from VworkOrderFixBill v where v.nodeState='已派工' and v.expired ='已超期' ")
    Long findExpired();
}
