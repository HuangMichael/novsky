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
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2016年10月9日11:46:27
 * 预防性维修业务类
 */
@Service
public class PreMaintService extends BaseService {

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


    public Page<VpreMaint> findByPmDescContains(String desc, Pageable pageable) {

        return vpreMaintRepository.findByPmDescContaining(desc, pageable);
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
    public List<Long> selectAllId() {

        return preMaintRepository.selectAllId();
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
     * @param id       根据id删除
     * @param deadLine
     * @return
     */
    @Transactional
    public List<PreMaintWorkOrder> generatePmOrder(Long id, String deadLine) {
        // TODO: 2016/10/20  根据id获取周期和单位
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
                nextDate = DateUtils.convertStr2Date(preMaint.getNextTime(), "yyyy-MM-dd");
                endDate = DateUtils.convertStr2Date(deadLine, "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("下次维修日期-----------" + nextDate.toString());
            System.out.println("选择截止日期-----------" + endDate.toString());
            // 先将下一个日期和选择日期对比
            while (nextDate.getTime() < endDate.getTime()) {
                PreMaintWorkOrder preMaintWorkOrder = new PreMaintWorkOrder();
                preMaintWorkOrder.setOrderLineNo(preMaint.getPmCode() + preMaintWorkOrderRepository.genPmOrderLineNo().get(0).toString());
                preMaintWorkOrder.setOrderDesc(preMaint.getDescription());
                preMaintWorkOrder.setCreator(preMaint.getCreateBy());
                preMaintWorkOrder.setEquipments(preMaint.getEquipment());
                preMaintWorkOrder.setLocations(preMaint.getEquipment().getLocations());
                preMaintWorkOrder.setLocation(preMaint.getLocation());
                preMaintWorkOrder.setEquipmentsClassification(preMaint.getEquipment().getEquipmentsClassification());
                preMaintWorkOrder.setReportType("p");
                preMaintWorkOrder.setUnit(preMaint.getOutUnit());
                preMaintWorkOrder.setReporter(preMaint.getCreateBy());
                preMaintWorkOrder.setReportTime(new Date());
                preMaintWorkOrder.setNodeState("已派工");
                preMaintWorkOrder = preMaintWorkOrderRepository.save(preMaintWorkOrder);
                pmOrderList.add(preMaintWorkOrder);
                preMaint.setLatestTime(DateUtils.convertDate2Str(nextDate, "yyyy-MM-dd"));
                nextDate = DateUtils.addDateByNumAndType(nextDate, frequency, unit);
                preMaint.setNextTime(DateUtils.convertDate2Str(nextDate, "yyyy-MM-dd"));

            }

            preMaintRepository.save(preMaint);
        }
        // 根据deadLine计算出周期数 按照每个周期生成工单
        return pmOrderList;
    }


    /**
     * @param nodeState 节点状态
     * @param orderDesc 维修描述
     * @param pageable
     * @return
     */
    public Page<VpreMaintOrder> findByNodeStateOrderDescContaining(String nodeState, String orderDesc, Pageable pageable) {
        return vpreMaintOrderRepository.findByNodeStateAndOrderDescContaining(nodeState, orderDesc, pageable);
    }
}
