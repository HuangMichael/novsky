package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.dao.workOrder.WorkOrderFixDetailRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderFixRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportDetailRepository;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.WorkOrderFix;
import com.linkbit.beidou.domain.workOrder.WorkOrderFixDetail;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.workOrder.WorkOrderFixService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportService;
import com.linkbit.beidou.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    WorkOrderReportDetailRepository workOrderReportDetailRepository;

    @Autowired
    WorkOrderFixRepository workOrderFixRepository;

    @Autowired
    WorkOrderFixDetailRepository workOrderFixDetailRepository;

    @Autowired

    WorkOrderFixService workOrderFixService;

   /* *//**
     * @param modelMap
     * @return 显示维修工单列表
     *//*
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        String location = user.getLocation();
        //过滤显示当前用户location数据 找出不完工的单子
        List<WorkOrderFix> workOrderFixList = workOrderFixRepository.findByLocationStartWithAndStatusLessThan(location + "%", CommonStatusType.ORDER_FIXED);
        modelMap.put("workOrderFixList", workOrderFixList);
        return "/workOrderFix/list";
    }*/


    /**
     * @param modelMap
     * @return 显示维修工单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {
        User user = SessionUtil.getCurrentUserBySession(session);
        String location = user.getLocation();
        //过滤显示当前用户location数据 找出不完工的单子

        List<WorkOrderFixDetail> workOrderFixDetailListList0 = workOrderFixService.findDistributedOrders(location);
        List<WorkOrderFixDetail> workOrderFixDetailListList1 = workOrderFixService.findFinishOrders(location);
        List<WorkOrderFixDetail> workOrderFixDetailListList2 = workOrderFixService.findPausedOrders(location);
        List<WorkOrderFixDetail> workOrderFixDetailListList3 = workOrderFixService.findRemovedOrders(location);
        modelMap.put("workOrderFixDetailListList0", workOrderFixDetailListList0);
        modelMap.put("workOrderFixDetailListList1", workOrderFixDetailListList1);
        modelMap.put("workOrderFixDetailListList2", workOrderFixDetailListList2);
        modelMap.put("workOrderFixDetailListList3", workOrderFixDetailListList3);
        //查询出已派工的维修单
        return "/workOrderFix/list";
    }

    /**
     * @param parentId
     * @param modelMap
     * @return 显示明细
     */
    @RequestMapping(value = "/detail/{parentId}", method = RequestMethod.GET)
    public String showDetail(@PathVariable Long parentId, ModelMap modelMap) {
        WorkOrderFix workOrderFix = workOrderFixRepository.findById(parentId);
        List<WorkOrderFixDetail> workOrderFixDetailList = workOrderFixDetailRepository.findByWorkOrderFix(workOrderFix);
        modelMap.put("workOrderFixDetailList", workOrderFixDetailList);
        return "/workOrderFix/detailList";
    }


    /**
     * @param parentId
     * @return 查询维修工单明细
     */
    @RequestMapping(value = "/findDetail/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderFixDetail> findDetail(@PathVariable Long parentId) {
        WorkOrderFix workOrderFix = workOrderFixRepository.findById(parentId);
        List<WorkOrderFixDetail> workOrderFixDetailList = workOrderFixDetailRepository.findByWorkOrderFix(workOrderFix);
        return workOrderFixDetailList;
    }

    /**
     * @param fixId
     * @return 完成单个维修工单
     */
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderFix finish(@RequestParam Long fixId, @RequestParam String orderDesc, HttpSession httpSession) {
        WorkOrderFix workOrderFix = workOrderFixRepository.findById(fixId);
        String personName = (String) httpSession.getAttribute("personName");
        workOrderFixService.finishBatch(fixId + ",", personName);
        return workOrderFix;
    }


    /**
     * @param fixId
     * @return 完成单个维修工单
     */
    @RequestMapping(value = "/finishDetail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject finishDetail(@RequestParam Long fixId, @RequestParam String fixDesc, HttpSession httpSession) {
        WorkOrderFixDetail workOrderFixDetail = workOrderFixDetailRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (!workOrderFixDetail.getStatus().equals("1")) {
            workOrderFixDetail.setStatus("1");
            workOrderFixDetail.setFixDesc(fixDesc);
            workOrderFixDetail = workOrderFixDetailRepository.save(workOrderFixDetail);
            String personName = (String) httpSession.getAttribute("personName");
            workOrderFixService.finishDetailBatch(fixId + "", personName);
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

        System.out.println("fixDesc--------------------" + fixDesc);
        WorkOrderFixDetail workOrderFixDetail = workOrderFixDetailRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (workOrderFixDetail.getStatus().equals("0")) {
            workOrderFixService.pauseDetailBatch(fixId + "", fixDesc);
            returnObject.setResult(true);
            returnObject.setResultDesc("维修单" + workOrderFixDetail.getOrderLineNo() + "暂停成功！");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("维修单已经处理,无法暂停！");
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
        WorkOrderFixDetail workOrderFixDetail = workOrderFixDetailRepository.findById(fixId);
        ReturnObject returnObject = new ReturnObject();
        if (workOrderFixDetail.getStatus().equals("0")) {
            workOrderFixDetail.setStatus("3");
            workOrderFixDetail.setFixDesc(fixDesc);
            workOrderFixDetail = workOrderFixDetailRepository.save(workOrderFixDetail);
            returnObject.setResult(true);
            returnObject.setResultDesc("维修单" + workOrderFixDetail.getOrderLineNo() + "取消成功！");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("维修单已经处理，无法取消！");
        }


        return returnObject;
    }

    /**
     * 暂停单个工单
     *
     * @param fixId
     * @return
     */
   /* @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderFix pause(@RequestParam Long fixId) {
        WorkOrderFix workOrderFix = workOrderFixRepository.findById(fixId);
        workOrderFix.setStatus("2");
        workOrderFix = workOrderFixRepository.save(workOrderFix);
        return workOrderFix;
    }*/
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @ResponseBody
    public List<WorkOrderFixDetail> pause(@RequestParam Long fixId) {
        List<WorkOrderFixDetail> workOrderFixDetailList = workOrderFixService.pauseDetailBatch(fixId + "", "");
        return workOrderFixDetailList;
    }


    /**
     * 批量完成
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/finishBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<WorkOrderFix> finishBatch(@RequestParam String ids) {
        List<WorkOrderFix> workOrderFixList = workOrderFixService.finishBatch(ids, ",");
        return workOrderFixList;
    }


    /**
     * 批量暂停维修工单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/pauseBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<WorkOrderFix> pauseBatch(@RequestParam String ids) {
        List<WorkOrderFix> workOrderFixList = workOrderFixService.pauseBatch(ids, ",");
        return workOrderFixList;
    }

    /**
     * 转单
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/transform/{id}", method = RequestMethod.GET)
    @ResponseBody
    public WorkOrderFix transform(@PathVariable Long id, ModelMap modelMap) {
        WorkOrderFix workOrderFix = workOrderFixService.transform(id);
        return workOrderFix;
    }


    @RequestMapping(value = "/findFinishedOrders/{location}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderFix> findFinishedOrders(@PathVariable String location) {
        List<WorkOrderFix> workOrderFixList = workOrderFixService.findFinishedOrders(location);
        return workOrderFixList;
    }


}
