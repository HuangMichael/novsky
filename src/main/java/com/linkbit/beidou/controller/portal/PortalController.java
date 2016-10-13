package com.linkbit.beidou.controller.portal;


import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.portal.PortalService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/portal")
@SessionAttributes("menuList")
public class PortalController {


    @Autowired
    WorkOrderReportCartService workOrderReportCartService;


    @Autowired
    PortalService portalService;


    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap) {

        return "/portal/index";
    }

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {

        return "/portal/list";
    }


    /**
     * @param offset 月份偏移量
     * @return
     */
    @RequestMapping(value = "/findTopEqClass/{offset}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findTopNReportCartByEqClass(@PathVariable(value = "offset") int offset) {
        // System.out.println(workOrderReportCartService.findTopNReportCartByEqClass(offset));
        return portalService.findTopNReportByEqClass(offset);
    }


    @RequestMapping(value = "/getLastNMonth/{n}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLastNMonth(@PathVariable("n") int n) {
        return workOrderReportCartService.getLastNMonthName(n);
    }


    @RequestMapping(value = "/getLineReportNum/{reportMonth}", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderLineNumReport> getLineReportNumReportMonth(@PathVariable("reportMonth") String reportMonth) {
        return portalService.getLineReportNumReportMonth(reportMonth);
    }


    @RequestMapping(value = "/getLineFixedNum/{reportMonth}", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderLineNumFixed> getLineFixedNumByReportMonth(@PathVariable("reportMonth") String reportMonth) {
        return portalService.getLineFixedNumByReportMonth(reportMonth);
    }


    @RequestMapping(value = "/getLineFixingNum/{reportMonth}", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderLineNumFixing> getLineFixingNumByReportMonth(@PathVariable("reportMonth") String reportMonth) {
        return portalService.getLineFixingNumByReportMonth(reportMonth);
    }

    @RequestMapping(value = "/getLineAbortNum/{reportMonth}", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderLineNumAbort> getLineAbortNumReportMonth(@PathVariable("reportMonth") String reportMonth) {
        return portalService.getLineAbortNumReportMonth(reportMonth);
    }


    @RequestMapping(value = "/getLineSuspendNum/{reportMonth}", method = RequestMethod.GET)
    @ResponseBody
    public List<VworkOrderLineNumSuspend> getLineSuspendNumByReportMonth(@PathVariable("reportMonth") String reportMonth) {
        return portalService.getLineSuspendNumByReportMonth(reportMonth);
    }


}

