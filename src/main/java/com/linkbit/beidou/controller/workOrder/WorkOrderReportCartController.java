package com.linkbit.beidou.controller.workOrder;


import com.linkbit.beidou.dao.workOrder.VworkOrderStepRepository;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.domain.workOrder.VworkOrderStep;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.locations.LocationsService;
import com.linkbit.beidou.service.workOrder.WorkOrderReportCartService;
import com.linkbit.beidou.utils.CommonStatusType;
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
@RequestMapping("/workOrderReportCart")
public class WorkOrderReportCartController {

    @Autowired
    WorkOrderReportCartService workOrderReportCartService;


    @Autowired
    LocationsService locationsService;


    @Autowired
    VworkOrderStepRepository vworkOrderStepRepository;

    /**
     * 显示所有的报修车列表信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpSession session) {

        User user = SessionUtil.getCurrentUserBySession(session);
        String userLocation = user.getLocation();
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.findByLocationsAndStatus(userLocation, CommonStatusType.CART_CREATED);

        System.out.println();
        modelMap.put("workOrderReportCartList", workOrderReportCartList);
        return "/workOrderReportCart/list";
    }


    /**
     * @param equipmentId 设备id
     * @return 在加入报修车之前检查是否已有该设备报修未完成的维修任务
     */
    @RequestMapping(value = "/checkEq/{equipmentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReportCart> checkBeforeAdd2Cart(@PathVariable("equipmentId") Long equipmentId) {
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.checkEquipmentBeforeAdd2Cart(equipmentId);
        return workOrderReportCartList;
    }

    /**
     * @param equipmentId 设备id
     * @return 在加入报修车之前检查是否已有该设备报修未完成的维修任务 objectList
     */
    @RequestMapping(value = "/checkEqs/{equipmentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> checkEqBeforeAdd2Cart(@PathVariable("equipmentId") Long equipmentId) {
        List<Object> workOrderReportCartList = workOrderReportCartService.checkEqsBeforeAdd2Cart(equipmentId);
        return workOrderReportCartList;
    }


    /**
     * @param equipmentId 设备id
     * @return 在加入报修车之前检查是否已有该设备报修未完成的维修任务 objectList
     */
    @RequestMapping(value = "/loadReportedEqPage/{equipmentId}", method = RequestMethod.GET)
    public String loadReportEqPage(@PathVariable("equipmentId") Long equipmentId, ModelMap modelMap) {
        List<Object> reportedEqList = workOrderReportCartService.checkEqsBeforeAdd2Cart(equipmentId);
        modelMap.put("reportedEqList", reportedEqList);
        return "/location/eqList";
    }


    /**
     * @param location 位置编号
     * @return 在加入报修车之前检查是否已有该设备报修未完成的维修任务 objectList
     */
    @RequestMapping(value = "/loadReportedLocPage/{location}", method = RequestMethod.GET)
    public String loadReportLocPage(@PathVariable("location") String location, ModelMap modelMap) {
        List<Object> reportedEqList = workOrderReportCartService.checkLocsBeforeAdd2Cart(location);
        modelMap.put("reportedEqList", reportedEqList);
        return "/location/locList";
    }


    /**
     * @param lid
     * @param modelMap
     * @return 在加入报修车之前检查是否已有该设备报修未完成的维修任务 objectList
     */
    @RequestMapping(value = "/loadWorkOrderStep/{lid}", method = RequestMethod.GET)
    public String loadWorkOrderStep(@PathVariable("lid") Long lid, ModelMap modelMap) {
        Locations locations = locationsService.findById(lid);
        List<VworkOrderStep> vworkOrderStepList = workOrderReportCartService.findByLocations(locations);
        modelMap.put("vworkOrderStepList", vworkOrderStepList);
        return "/location/locList";
    }


    /**
     * @param locationId 位置id
     * @return 在加入报修车之前检查是否已有该位置报修未完成的维修任务
     */
    @RequestMapping(value = "/checkLoc/{locationId}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReportCart> checkLoc(@PathVariable("locationId") Long locationId) {
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.checkLocationBeforeAdd2Cart(locationId);
        return workOrderReportCartList;
    }


    /**
     * 设备报修添加到报修车
     *
     * @param equipmentId 设备id
     * @param httpSession 当前会话
     * @return
     */
    @RequestMapping(value = "/add2Cart", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderReportCart add2Cart(@RequestParam("equipmentId") Long equipmentId, HttpSession httpSession) {
        String personName = (String) httpSession.getAttribute("personName");
        WorkOrderReportCart workOrderReportCart = workOrderReportCartService.add2Cart(equipmentId, personName);
        return workOrderReportCart;
    }


    /**
     * 位置报修添加到报修车
     *
     * @param locationId  位置id
     * @param orderDesc   报修描述
     * @param httpSession 当前会话
     * @return
     */
    @RequestMapping(value = "/add2LocCart", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderReportCart add2LocCart(@RequestParam("locationId") Long locationId, @RequestParam("orderDesc") String orderDesc, @RequestParam("reporter") String reporter, @RequestParam("eqClassId") Long eqClassId, HttpSession httpSession) {
        String creator = (String) httpSession.getAttribute("personName");
        WorkOrderReportCart workOrderReportCart = workOrderReportCartService.add2LocCart(locationId, orderDesc, creator, reporter, eqClassId);
        return workOrderReportCart;
    }

    /**
     * 显示所有的报修车列表信息
     */
    @RequestMapping(value = "/findMyCart/{n}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkOrderReportCart> findMyCart(@PathVariable("n") Long n, HttpSession httpSession) {
        String personName = (String) httpSession.getAttribute("personName");
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.findMyCart(personName);
        return workOrderReportCartList;
    }


    /**
     * 显示所有的报修车列表信息
     */
    @RequestMapping(value = "/findMyCartSize", method = RequestMethod.GET)
    @ResponseBody
    public Long findMyCartSize(HttpSession httpSession) {
        String personName = (String) httpSession.getAttribute("personName");
        Long myCartSize = workOrderReportCartService.findMyCartSize(personName);
        return myCartSize;
    }


    /**
     * 加载将要提交的报修车信息
     */
    @RequestMapping(value = "/loadDetailList", method = RequestMethod.GET)
    public String loadDetailList(ModelMap modelMap) {
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.findByStatus("1");
        modelMap.put("workOrderReportCartList", workOrderReportCartList);
        return "/workOrderReportCart/resultList";
    }


    /**
     * 移除购物车信息
     */
    @RequestMapping(value = "/delCart", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject delCart(@RequestParam Long id) {
        ReturnObject returnObject = new ReturnObject();
        WorkOrderReportCart workOrderReportCart = workOrderReportCartService.delCart(id);
        returnObject.setResult(workOrderReportCart == null);
        String resultDesc = "报修信息移报修车";
        resultDesc += returnObject.getResult() ? "成功！" : "失败";
        returnObject.setResultDesc(resultDesc);
        return returnObject;
    }


    /**
     * 加载将要提交的报修车信息
     */
    @RequestMapping(value = "/loadOrderDesc", method = RequestMethod.POST)
    public String loadOrderDesc(@RequestParam("ids") String ids, ModelMap modelMap) {
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.findWorkOrderReportCartByIds(ids);
        modelMap.put("workOrderReportCartList", workOrderReportCartList);
        return "/workOrderReportCart/orderDescList";
    }


    /**
     * 加载将要提交的报修车确认信息
     */
    @RequestMapping(value = "/loadCommitPage", method = RequestMethod.POST)
    public String loadCommitPage(@RequestParam("ids") String ids, ModelMap modelMap) {
        List<WorkOrderReportCart> workOrderReportCartList = workOrderReportCartService.findWorkOrderReportCartByIds(ids);
        modelMap.put("workOrderReportCartList", workOrderReportCartList);
        return "/workOrderReportCart/orderCommitList";
    }


    /**
     * 更新维修描述
     */
    @RequestMapping(value = "/updateOrderDesc", method = RequestMethod.POST)
    @ResponseBody
    public WorkOrderReportCart updateOrderDesc(@RequestParam("id") Long id, @RequestParam("orderDesc") String orderDesc) {
        WorkOrderReportCart workOrderReportCart = workOrderReportCartService.updateOrderDesc(id, orderDesc);
        return workOrderReportCart;
    }


}
