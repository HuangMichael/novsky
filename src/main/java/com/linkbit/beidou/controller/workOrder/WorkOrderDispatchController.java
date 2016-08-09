package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.WorkOrderFix;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportDetail;
import com.linkbit.beidou.service.workOrder.WorkOrderDispatchService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import com.linkbit.beidou.utils.CommonStatusType;
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
@RequestMapping("/workOrderDispatch")
public class WorkOrderDispatchController {


    @Autowired
    WorkOrderDispatchService workOrderDispatchService;
    @Autowired
    WorkOrderReportService workOrderReportService;

    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;

    /**
     * @param modelMap
     * @return 显示报修工单明细列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        //查询当前登录用户location下的未完工的报修单明细
        List<WorkOrderReportDetail> workOrderReportDetailList = null;

        List<OutsourcingUnit> unitList = null;
        if (user != null && user.getLocation() != null) {
            workOrderReportDetailList = workOrderDispatchService.findReportList(user.getLocation(), CommonStatusType.ORDER_DISTRIBUTED);
            unitList = outsourcingUnitRepository.findByStatus("1");
            modelMap.put("workOrderReportDetailList", workOrderReportDetailList);
            modelMap.put("unitList", unitList);

        }
        return "/workOrderDispatcher/list";
    }


    /**
     * @param ids          选择报修信息id列表
     * @param selectedType 选择的规约方式
     * @param session      用户当前会话
     * @return 按照用户自定义类型进行规约
     */
    @RequestMapping(value = "/mapBySelectedType", method = RequestMethod.POST)
    public String mapBySelectedType(@RequestParam("ids") String ids, @RequestParam("selectedType") String selectedType, HttpSession session, ModelMap modelMap) {
        List list = workOrderReportService.mapBySelectedType(ids, selectedType);
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        User user = SessionUtil.getCurrentUserBySession(session);
        if (user != null && user.getPerson() != null && user.getLocation() != null) {
            workOrderFixList = workOrderReportService.preViewFixReport(list, user.getPerson().getPersonName(), user.getLocation());
        }
        modelMap.put("workOrderFixList", workOrderFixList);
        return "/workOrderDispatch/report-view-list";
    }


    /**
     * @param ids          选择报修信息id列表
     * @param selectedType 选择的规约方式
     * @param session      用户当前会话
     * @return 按照用户自定义类型进行规约
     */
    @RequestMapping(value = "/generateFixRptByType", method = RequestMethod.POST)
    @ResponseBody
    public List<WorkOrderFix> generateFixRptByType(@RequestParam("ids") String ids, @RequestParam("selectedType") String selectedType, HttpSession session) {
        List list = workOrderReportService.mapBySelectedType(ids, selectedType);
        List<WorkOrderFix> workOrderFixList = new ArrayList<WorkOrderFix>();
        User user = SessionUtil.getCurrentUserBySession(session);
        if (user != null && user.getPerson() != null && user.getLocation() != null) {
            workOrderFixList = workOrderReportService.createReport(list, user.getPerson().getPersonName(), user.getLocation());
        }
        return workOrderFixList;
    }


    /**
     * 加载将要生成维修单的维修信息
     */
    @RequestMapping(value = "/loadOrderDesc", method = RequestMethod.POST)
    public String loadOrderDesc(@RequestParam("ids") String ids, ModelMap modelMap) {
        List<WorkOrderReportDetail> WorkOrderReportDetailList = workOrderDispatchService.findWorkOrderReportDetailByIds(ids);
        modelMap.put("WorkOrderReportDetailList", WorkOrderReportDetailList);
        return "/workOrderDispatcher/orderDescList";
    }


    /**
     * @param detailId 维修明细id
     * @param unitId   维修单位ID
     * @return
     */
    @RequestMapping(value = "/updateDetailUnit", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderReportDetail updateDetailUnit(@RequestParam("detailId") Long detailId, @RequestParam("unitId") Long unitId) {
        WorkOrderReportDetail workOrderReportDetail = workOrderDispatchService.updateDetailUnit(detailId, unitId);
        return workOrderReportDetail;
    }


    /**
     * 加载将要提交的报修车确认信息
     */
    @RequestMapping(value = "/loadCommitPage", method = RequestMethod.POST)
    public String loadCommitPage(@RequestParam("ids") String ids, ModelMap modelMap) {
        List<WorkOrderReportDetail> workOrderReportDetailList = workOrderDispatchService.findWorkOrderReportCartByIds(ids);
        modelMap.put("workOrderReportDetailList", workOrderReportDetailList);
        return "/workOrderDispatcher/orderCommitList";
    }
}
