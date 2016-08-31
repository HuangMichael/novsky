package com.linkbit.beidou.controller.equipment;


import com.linkbit.beidou.dao.equipments.EqRepository;
import com.linkbit.beidou.dao.equipments.VEqRepository;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 可分页控制器
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/eq")
public class EqController {
    @Autowired
    EqRepository repository;

    @Autowired
    VEqRepository vEqRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String  list() {
        return "/eq/list";

    }

    @RequestMapping(value = "/listVeq/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Vequipments> listVeq(@PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize")int pageSize ) {
        Page<Vequipments> equipmentsPage = vEqRepository.findAll(new PageRequest(pageIndex,pageSize));
        return equipmentsPage;

    }
}
