package com.linkbit.beidou.controller.equipment;


import com.linkbit.beidou.dao.equipments.EqRepository;
import com.linkbit.beidou.dao.equipments.VEqRepository;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.equipments.EquipmentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


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
    public Page<Vequipments> listVeq(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
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
}
