package com.linkbit.beidou.service.preMaint;

import com.linkbit.beidou.dao.preMaint.PreMaintRepository;
import com.linkbit.beidou.dao.preMaint.PreMaintWorkOrderRepository;
import com.linkbit.beidou.dao.preMaint.VpreMaintOrderRepository;
import com.linkbit.beidou.dao.preMaint.VpreMaintRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportCartRepository;
import com.linkbit.beidou.domain.preMaint.PreMaint;
import com.linkbit.beidou.domain.preMaint.PreMaintWorkOrder;
import com.linkbit.beidou.domain.preMaint.VpreMaint;
import com.linkbit.beidou.domain.preMaint.VpreMaintOrder;
import com.linkbit.beidou.domain.workOrder.WorkOrderHistory;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.DateUtils;
import com.linkbit.beidou.utils.LocationSeparatable;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by huangbin on 2016年10月9日11:46:27
 * 预防性维修业务类
 */
@Service
@Data
public class PreMaintService extends BaseService implements LocationSeparatable {

    @Autowired
    VpreMaintRepository vpreMaintRepository;

    @Autowired
    PreMaintRepository preMaintRepository;
    @Autowired
    PreMaintWorkOrderRepository preMaintWorkOrderRepository;


    @Autowired
    VpreMaintOrderRepository vpreMaintOrderRepository;


    /**
     * @return 查询所有
     */
    public Page<PreMaint> findAll(Pageable pageable) {

        return preMaintRepository.findAll(pageable);
    }

    /**
     * @return 查询所有
     */
    public Page<VpreMaint> findAllv(Pageable pageable) {

        return vpreMaintRepository.findAll(pageable);
    }


    /**
     * @param desc     预防性维修描述
     * @param location 位置编号
     * @param pageable 可分页
     * @return
     */
    public Page<VpreMaint> findByPmDescContainingAndLocationStartingWith(String desc, String location, Pageable pageable) {

        return vpreMaintRepository.findByPmDescContainingAndLocationStartingWith(desc, location, pageable);
    }


    public List<VpreMaint> findByPmDescContains(String desc) {

        return vpreMaintRepository.findByPmDescContaining(desc);
    }


    /**
     * @param id 根据id查询
     * @return
     */
    public PreMaint findById(Long id) {

        return preMaintRepository.findOne(id);
    }

    /**
     * @return
     */
    public List<Long> selectAllId(String location) {
        if (separatable) {
            location = location + "%";
        } else {
            location = "";
        }
        return preMaintRepository.selectAllId(location);
    }

    /**
     * @param preMaint 预防性维修信息
     * @return 保存预防性维修信息
     */
    public PreMaint save(PreMaint preMaint) {
        return preMaintRepository.save(preMaint);
    }


    /**
     * @param id 根据id删除
     * @return
     */
    public boolean delete(Long id) {
        preMaintRepository.delete(id);
        return preMaintRepository.findOne(id) == null;
    }


    /**
     * @param id       预防性计划id
     * @param deadLine 结束时间
     * @return
     */
    @Transactional
    public List<PreMaintWorkOrder> generatePmOrder(Long id, String deadLine) {
        List<PreMaintWorkOrder> pmOrderList = new ArrayList<PreMaintWorkOrder>();
        PreMaint preMaint = preMaintRepository.findOne(id);
        int frequency, unit;
        Date endDate = null;
        Date nextDate = null;
        if (null != preMaint) {
            frequency = preMaint.getFrequency();
            unit = preMaint.getUnit();
            // 1 DAY 2 MONTH 3 YEAR
            try {
                String nextTime = preMaint.getNextTime();
                if (nextTime == null || nextTime.equals("")) {
                    preMaint.setNextTime(DateUtils.convertDate2Str(new Date(), "yyyy-MM-dd"));
                }
                nextDate = DateUtils.convertStr2Date(preMaint.getNextTime(), "yyyy-MM-dd");
                endDate = DateUtils.convertStr2Date(deadLine, "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 先将下一个日期和选择日期对比

            int i = 0;
            while (nextDate.getTime() < endDate.getTime()) {
                PreMaintWorkOrder preMaintWorkOrder = new PreMaintWorkOrder();
                preMaintWorkOrder.setOrderLineNo(preMaint.getPmCode() + new Random().nextInt());
                preMaintWorkOrder.setOrderDesc(preMaint.getDescription() + "-" + i);
                preMaintWorkOrder.setCreator(preMaint.getCreateBy());
                preMaintWorkOrder.setEquipments(preMaint.getEquipment());
                preMaintWorkOrder.setLocation(preMaint.getEquipment().getLocations().getLocation());
                preMaintWorkOrder.setEquipmentsClassification(preMaint.getEquipment().getEquipmentsClassification());
                preMaintWorkOrder.setReportType("p");
                preMaintWorkOrder.setUnit(preMaint.getOutUnit());
                preMaintWorkOrder.setReporter(preMaint.getCreateBy());
                preMaintWorkOrder.setReportTime(new Date());
                preMaintWorkOrder.setPreMaint(preMaint);
                preMaintWorkOrder.setNodeState("已派工");
                preMaintWorkOrder = preMaintWorkOrderRepository.save(preMaintWorkOrder);
                pmOrderList.add(preMaintWorkOrder);
                //如果最近时间为空  赋值当前时间
                if (preMaint.getLatestTime() == null) {
                    preMaint.setLatestTime(DateUtils.convertDate2Str(new Date(), "yyyy-MM-dd"));
                }
                preMaint.setLatestTime(DateUtils.convertDate2Str(nextDate, "yyyy-MM-dd"));
                nextDate = DateUtils.addDateByNumAndType(nextDate, frequency, unit);
                preMaint.setNextTime(DateUtils.convertDate2Str(nextDate, "yyyy-MM-dd"));
                i++;
            }

            preMaintRepository.save(preMaint);
        }
        // 根据deadLine计算出周期数 按照每个周期生成工单
        return pmOrderList;
    }


    /**
     * @param nodeState 节点状态
     * @param orderDesc 维修描述
     * @param pageable  可分页
     * @return
     */
    public Page<VpreMaintOrder> findByNodeStateOrderDescContaining(String nodeState, String orderDesc, Pageable pageable) {
        return vpreMaintOrderRepository.findByNodeStateAndOrderDescContaining(nodeState, orderDesc, pageable);
    }





    /**
     * @param nodeState 节点状态
     * @param orderDesc 维修描述
     * @return
     */
    public List<VpreMaintOrder> findByNodeStateOrderDescContaining(String nodeState, String orderDesc) {
        return vpreMaintOrderRepository.findByNodeStateAndOrderDescContaining(nodeState, orderDesc);
    }

    /**
     * @param preMaintWorkOrder 预防性维修工单
     * @param fixDesc           维修描述
     * @param status            工单状态
     * @return
     */
    @Transactional
    public PreMaintWorkOrder handleWorkOrder(PreMaintWorkOrder preMaintWorkOrder, String fixDesc, String status) {
        PreMaintWorkOrder saved = null;
        if (!preMaintWorkOrder.getNodeState().equals(status)) {
            preMaintWorkOrder.setStatus("1");
            preMaintWorkOrder.setNodeState(status);
            preMaintWorkOrder.setFixDesc(fixDesc);
            preMaintWorkOrder.setLastStatusTime(new Date());
            saved = preMaintWorkOrderRepository.save(preMaintWorkOrder);
        }
        return saved;
    }


    /**
     * @param orderDesc
     * @return 根据工单描述模糊查询
     */
    public List<PreMaintWorkOrder> findByOrderDescContains(String orderDesc) {
        return preMaintWorkOrderRepository.findByOrderDesc(orderDesc);
    }
}
