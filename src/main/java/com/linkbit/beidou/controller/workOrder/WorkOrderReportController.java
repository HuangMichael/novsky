package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.VworkOrderNumFinish;
import com.linkbit.beidou.domain.workOrder.VworkOrderNumReport;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.service.workOrder.WorkOrderReportCartService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import com.linkbit.beidou.utils.SessionUtil;
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
@RequestMapping("/workOrderReport")
public class WorkOrderReportController {


    @Autowired
    WorkOrderReportService workOrderReportService;

    @Autowired
    WorkOrderReportCartService workOrderReportCartService;

    /**
     * 保存工单信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        String location = user.getLocation();
        List<WorkOrderReportCart> workOrderReportDetailList = workOrderReportCartService.findByLocationStartingWith(location);
        modelMap.put("workOrderReportDetailList", workOrderReportDetailList);
        return "/workOrderReport/list";
    }


    /**
     * 批量更新设备故障描述
     */
    @RequestMapping(value = "/generateReport", method = RequestMethod.POST)
    @ResponseBody
    public List<WorkOrderReportCart> generateReport(@RequestParam("ids") String ids, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        List<WorkOrderReportCart> workOrderReportDetailList = new ArrayList<WorkOrderReportCart>();
        if (user != null && user.getLocation() != null && user.getPerson() != null) {
            workOrderReportDetailList = workOrderReportService.generateReport(ids);
        }
        return workOrderReportDetailList;

    }


    /**
     * @return 按照设备分类进行规约
     * //生成历史信息  并且更新状态
     */
    @RequestMapping(value = "/mapByUnitId", method = RequestMethod.POST)
    @ResponseBody
    public List mapByUnitId(@RequestParam("ids") String ids) {
        return workOrderReportService.mapByUnitId(ids);
    }

    /**
     * @return 查询近期三个月的报修单
     */
    @RequestMapping(value = "/sel3mRptNum", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderNumReport> selectReportNumIn3Months() {
        return workOrderReportService.selectReportNumIn3Months();
    }

    /**
     * @return 查询近期三个月的报修单
     */
    @RequestMapping(value = "/sel3mFinishNum", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderNumFinish> selectFinishNumIn3Months() {
        return workOrderReportService.selectFinishNumIn3Months();
    }
}
