package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.WorkOrderFix;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportDetail;
import com.linkbit.beidou.service.workOrder.WorkOrderFixService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/workOrderAllocation")
public class WorkOrderAllocationController {


    @Autowired
    WorkOrderReportService workOrderReportService;
    @Autowired
    WorkOrderFixService workOrderFixService;


    /**
     * 保存工单信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        List<WorkOrderReportDetail> workOrderReportList = workOrderReportService.findOrderDetailListByStatus("0");
        modelMap.put("workOrderReportList", workOrderReportList);
        return "/workOrderAllocation/list";
    }


    /**
     * 分配工单信息
     */
    @RequestMapping(value = "/allocate", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderFix allocate(@RequestParam("array") String arrayStr, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        String reporter = user != null ? user.getPerson().getPersonName() : "";
        WorkOrderFix workOrderFix = workOrderFixService.generateFix(arrayStr, reporter);
        return workOrderFix;
    }


    /**
     * 保存工单信息
     */
    @RequestMapping(value = "/eq", method = RequestMethod.GET)
    @ResponseBody
    public List<EquipmentsClassification> eq() {
        List<Long> idList =  new ArrayList<Long>();
        idList.add(4l);
        idList.add(5l);
        idList.add(6l);
        List<EquipmentsClassification> equipmentsClassificationList = workOrderFixService.findEqClassByIds(idList);
        return equipmentsClassificationList;
    }


}
