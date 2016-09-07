package com.linkbit.beidou.controller.equipment;


import com.linkbit.beidou.dao.equipments.EqRepository;
import com.linkbit.beidou.dao.equipments.VEqRepository;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.object.SearchObject;
import com.linkbit.beidou.object.eq.EqSearchObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.equipments.EquipmentAccountService;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * 可分页控制器
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/eq")
public class EqController {

    @Autowired
    EquipmentAccountService equipmentAccountService;
    @Autowired
    EqRepository repository;

    @Autowired
    VEqRepository vEqRepository;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpSession httpSession, ModelMap modelMap) {
        List<VRoleAuthView> appMenus = resourceService.findAppMenusByController(httpSession, "equipment");
        modelMap.put("appMenus", appMenus);
        return "/eq/list";

    }

    @RequestMapping(value = "/listVeq/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Vequipments> listVeq(@PathVariable(value = "pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
        Page<Vequipments> equipmentsPage = vEqRepository.findAll(new PageRequest(pageIndex, pageSize));
        return equipmentsPage;

    }


    @RequestMapping(value = "/loadPage/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public String loadPage(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize, ModelMap modelMap) {
        Page<Vequipments> equipmentsPage = vEqRepository.findAll(new PageRequest(pageIndex, pageSize));
        List<Vequipments> vequipmentsList = equipmentsPage.getContent();
        modelMap.put("vequipmentsList", vequipmentsList);
        return "/eq/eqList";

    }


    /**
     * @return 根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(ModelMap modelMap, @RequestParam("eqCode") String eqCode) {
        List<Vequipments> vequipmentsList = equipmentAccountService.search(eqCode);
        modelMap.put("vequipmentsList", vequipmentsList);
        return "/eq/eqList";
    }

    /**
     * @param eqName
     * @param locName
     * @param eqClass
     * @param pageIndex
     * @param pageCount
     * @param modelMap
     * @return 模糊查询设备分页信息
     */
    @RequestMapping(value = "/queryByLike/{eqName}/{locName}/{eqClass}/{pageIndex}/{pageCount}", method = RequestMethod.GET)
    public String queryByLike(
            @PathVariable(value = "eqName") String eqName,
            @PathVariable(value = "locName") String locName,
            @PathVariable(value = "eqClass") String eqClass,
            @PathVariable(value = "pageIndex") int pageIndex,
            @PathVariable(value = "pageCount") int pageCount,

            ModelMap modelMap) {
        List<Object> vequipmentsList = vEqRepository.myQuery(eqName, locName, eqClass, pageIndex, pageCount);
        modelMap.put("vequipmentsList", vequipmentsList);
        return "/eq/eqList";
    }


    /**
     * @param eqName
     * @param locName
     * @param eqClass
     * @param modelMap
     * @return 模糊查询设备分页信息
     */
    @RequestMapping(value = "/queryByLike/{eqName}/{locName}/{eqClass}", method = RequestMethod.GET)
    public String queryByLike(
            @PathVariable(value = "eqName") String eqName,
            @PathVariable(value = "locName") String locName,
            @PathVariable(value = "eqClass") String eqClass, ModelMap modelMap) {
        List<Vequipments> vequipmentsList = vEqRepository.findByEqNameContainsAndLocNameContainsAndEqClassContains(eqName, locName, eqClass);
        System.out.println("size---------------------" + vequipmentsList.size());
        modelMap.put("vequipmentsList", vequipmentsList);
        return "/eq/eqList";
    }
}
