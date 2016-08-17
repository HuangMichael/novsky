package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.dao.workOrder.WorkOrderHistoryRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportCartRepository;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.WorkOrderHistory;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.workOrder.WorkOrderFixService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import com.linkbit.beidou.utils.DateUtils;
import com.linkbit.beidou.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/workOrderFix")
public class WorkOrderFixController {


    @Autowired
    WorkOrderReportService workOrderReportService;


    @Autowired

    WorkOrderFixService workOrderFixService;


    @Autowired
    WorkOrderHistoryRepository workOrderHistoryRepository;

    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;

    /**
     * @param modelMap
     * @return 显示维修工单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        String location = user.getLocation();
        //过滤显示当前用户location数据 找出不完工的单子

        List<WorkOrderReportCart> workOrderFixDetailListList0 = workOrderFixService.findDistributedOrders(location);
        List<WorkOrderReportCart> workOrderFixDetailListList1 = workOrderFixService.findFinishOrders(location);
        List<WorkOrderReportCart> workOrderFixDetailListList2 = workOrderFixService.findPausedOrders(location);
        List<WorkOrderReportCart> workOrderFixDetailListList3 = workOrderFixService.findRemovedOrders(location);
        modelMap.put("workOrderFixDetailListList0", workOrderFixDetailListList0);
        modelMap.put("workOrderFixDetailListList1", workOrderFixDetailListList1);
        modelMap.put("workOrderFixDetailListList2", workOrderFixDetailListList2);
        modelMap.put("workOrderFixDetailListList3", workOrderFixDetailListList3);
        //查询出已派工的维修单
        return "/workOrderFix/list";
    }


    /**
     * @param fixId
     * @return 完成单个维修工单
     */
    @RequestMapping(value = "/finishDetail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject finishDetail(@RequestParam Long fixId, @RequestParam String fixDesc) {
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (!workOrderReportCart.getNodeState().equals("已完工")) {
            workOrderReportCart.setStatus("1");
            workOrderReportCart.setNodeState("已完工");
            workOrderReportCart.setFixDesc(fixDesc);
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);

            workOrderFixService.updateNodeStatus(workOrderReportCart);

            //插入一条最新状态记录
            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistory.setStatus("1");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setNodeDesc("已完工");
            workOrderHistory = workOrderHistoryRepository.save(workOrderHistory);

            //将其他节点状态修改为0
            returnObject.setResult(true);
            returnObject.setResultDesc("维修单已完工！");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("维修单完工失败！");
        }
        return returnObject;
    }


    /**
     * @param fixId
     * @return 完成单个维修工单
     */
    @RequestMapping(value = "/pauseDetail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject pauseDetail(@RequestParam Long fixId, @RequestParam String fixDesc) {
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (!workOrderReportCart.getNodeState().equals("已暂停")) {
            workOrderReportCart.setStatus("1");
            workOrderReportCart.setNodeState("已暂停");
            workOrderReportCart.setFixDesc(fixDesc);
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);

            workOrderFixService.updateNodeStatus(workOrderReportCart);

            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistory.setStatus("1");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setNodeDesc("已暂停");
            workOrderHistoryRepository.save(workOrderHistory);
            returnObject.setResult(true);
            returnObject.setResultDesc("维修单已暂停！");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("维修单无法暂停！");
        }
        return returnObject;
    }

    /**
     * @param fixId
     * @return 取消单个维修工单
     */
    @RequestMapping(value = "/abortDetail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject abortDetail(@RequestParam Long fixId, @RequestParam String fixDesc) {
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (!workOrderReportCart.getNodeState().equals("已取消")) {
            workOrderReportCart.setStatus("0");
            workOrderReportCart.setNodeState("已取消");
            workOrderReportCart.setFixDesc(fixDesc);
            workOrderReportCart = workOrderReportCartRepository.save(workOrderReportCart);
            //更新节点状态
            workOrderFixService.updateNodeStatus(workOrderReportCart);
            WorkOrderHistory workOrderHistory = new WorkOrderHistory();
            workOrderHistory.setWorkOrderReportCart(workOrderReportCart);
            workOrderHistory.setStatus("1");
            workOrderHistory.setNodeTime(new Date());
            workOrderHistory.setNodeDesc("已取消");
            workOrderHistoryRepository.save(workOrderHistory);
            returnObject.setResult(true);
            returnObject.setResultDesc("维修单已取消！");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("维修单无法取消！");
        }
        return returnObject;
    }


    /**
     * @param orderId
     * @return 取消单个维修工单
     */
    @RequestMapping(value = "/getCellingDate/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Date getCellingDate(@PathVariable("orderId") Long orderId) {
        //根据维修单id查询分配订单时间
        Date outDate = null;
        WorkOrderReportCart workOrderReportCart = workOrderReportCartRepository.findById(orderId);
        List<WorkOrderHistory> workOrderHistoryList = workOrderHistoryRepository.findByWorkOrderReportCart(workOrderReportCart);
        if (!workOrderHistoryList.isEmpty()) {
            Date dispatchTime = workOrderHistoryList.get(0).getNodeTime();
            outDate = DateUtils.getCellingDate(dispatchTime);
        }
        return outDate;
    }
}
