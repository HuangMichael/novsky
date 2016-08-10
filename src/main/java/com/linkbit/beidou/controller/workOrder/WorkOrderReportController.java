package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.*;
import com.linkbit.beidou.service.workOrder.WorkOrderReportCartService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import com.linkbit.beidou.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
     */
    @RequestMapping(value = "/mapByType", method = RequestMethod.POST)
    @ResponseBody
    public List mapByType(@RequestParam("ids") String ids, HttpSession session) {
        List list = workOrderReportService.mapByType(ids);
        User user = SessionUtil.getCurrentUserBySession(session);
        if (user != null && user.getPerson() != null && user.getLocation() != null)
            workOrderReportService.createReport(list, user.getPerson().getPersonName(), user.getLocation());
        return list;
    }

    /**
     * @return 按照设备分类进行规约
     */
    @RequestMapping(value = "/mapByUnitId", method = RequestMethod.POST)
    @ResponseBody
    public List mapByUnitId(@RequestParam("ids") String ids, HttpSession session) {

//生成历史信息  并且更新状态


        return workOrderReportService.mapByUnitId(ids);
    }

    @RequestMapping(value = "/findReportHistory/{equipmentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReportDetail> findReportHistory(@PathVariable("equipmentId") Long equipmentId) {
        List<WorkOrderReportDetail> workOrderReportDetailList = new ArrayList<WorkOrderReportDetail>();
        if (equipmentId != null && equipmentId != 0) {
            workOrderReportDetailList = workOrderReportService.findReportHistoryByEquipmentId(equipmentId);
        }
        return workOrderReportDetailList;
    }
    //查询已经提交的报修单

    /**
     * @param httpSession
     * @return 查询没有提交的维修单
     */
    @RequestMapping(value = "/findCommitted", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReport> findCommittedReportedOrders(HttpSession httpSession) {

        String location = SessionUtil.getCurrentUserLocationBySession(httpSession);
        return workOrderReportService.findByLocationStartingWithAndStatus(location, "1");
    }

    /**
     * @param httpSession
     * @return 查询没有被完全提交的维修单
     */
    @RequestMapping(value = "/findNew", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReport> findNewReportedOrders(HttpSession httpSession) {
        String location = SessionUtil.getCurrentUserLocationBySession(httpSession);
        return workOrderReportService.findByLocationStartingWithAndStatus(location, "0");
    }


    /**
     * @param httpSession
     * @return 查询没有被完全提交的维修单
     */
    @RequestMapping(value = "/findNewDetails", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReportDetail> findNewReportedOrdersDetail(HttpSession httpSession) {
        String location = SessionUtil.getCurrentUserLocationBySession(httpSession);
        return workOrderReportService.findByLocationStartingWithAndStatusOrderByReportTimeDesc(location, "0");
    }

    /**
     * @param perPageCount     每页显示多少条记录
     * @param currentPageIndex 当前是第几页
     * @return 查询没有被完全提交的维修单
     */
    @RequestMapping(value = "/getRecortsByPage/{perPageCount}/{currentPageIndex}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getRecortsByPage(@PathVariable("perPageCount") Long perPageCount, @PathVariable("currentPageIndex") Long currentPageIndex) {

        return workOrderReportService.getRecortsByPage(perPageCount * currentPageIndex, perPageCount);
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
