package com.linkbit.beidou.controller.locations;

import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.equipments.VequipmentsRepository;
import com.linkbit.beidou.dao.locations.LocationsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.domain.line.Station;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.equipments.EquipmentAccountService;
import com.linkbit.beidou.service.line.LineService;
import com.linkbit.beidou.service.line.StationService;
import com.linkbit.beidou.service.locations.LocationsService;
import com.linkbit.beidou.utils.SessionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 * 位置控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/location")
public class LocationController {

    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LocationsService locationsService;

    @Autowired
    StationService stationService;

    @Autowired
    LineService lineService;
    @Autowired
    EquipmentAccountService equipmentAccountService;

    @Autowired
    EquipmentsRepository equipmentsRepository;

    List<Line> lineList;

    List<Station> stationList;

    @Autowired
    LocationsRepository locationsRepository;
    @Autowired
    VlocationsRepository vlocationsRepository;
    @Autowired
    VequipmentsRepository vequipmentsRepository;


    /**
     * @param modelMap
     * @param session
     * @return 初始化载入界面
     */
    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        List<Vequipments> equipmentsList = vequipmentsRepository.findByLocationStartingWithOrderByIdDesc(user.getLocation());
        modelMap.put("equipmentsList", equipmentsList);
        return "/location/list";

    }

    /**
     * @param id
     * @param modelMap
     * @param session
     * @return 根据ID显示位置信息 显示明细
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap, HttpSession session) {
        String url = "/location";
        Locations object = null;
        if (id != 0) {
            url += "/detail";
            object = locationsService.findById(id);
        }
        modelMap.put("locations", object);
        List<Vequipments> equipmentsList = vequipmentsRepository.findByLocationStartingWith(object.getLocation());
        modelMap.put("equipmentsList", equipmentsList);
        return url;
    }


    /**
     * @param request
     * @return 查询所有的位置信息
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Locations> findAll(HttpServletRequest request) {
        List<Locations> locationsList = (List<Locations>) request.getSession().getAttribute("locationsList");
        if (locationsList.isEmpty()) {
            locationsList = locationsService.findAll();
        }

        return locationsList;
    }


    /**
     * @param httpSession 当前会话
     * @return 查询的位置树节点集合
     */
    @RequestMapping(value = "/findTree")
    @ResponseBody
    public List<Object> findTree(HttpSession httpSession) {
        List<Object> objectList = null;
        User user = SessionUtil.getCurrentUserBySession(httpSession);
        if (user.getLocation() != null && !user.getLocation().equals("")) {
            objectList = locationsService.findTree(user.getLocation() + "%");
        }
        return objectList;
    }

    /**
     * @param locLevel 节点级别
     * @return 查询节点级别小于 locLevel的记录
     */
    @RequestMapping(value = "/findByLocLevelLessThan/{locLevel}")
    @ResponseBody
    public List<Locations> findBylocLevelLessThan(@PathVariable("locLevel") Long locLevel) {
        List<Locations> locationsList = locationsService.findByLocLevelLessThan(locLevel);
        return locationsList;
    }


    /**
     * @param id
     * @param modelMap
     * @param session
     * @return 新建位置信息
     */
    @RequestMapping(value = "/create/{id}")
    public String create(@PathVariable("id") Long id, ModelMap modelMap, HttpSession session) {
        Locations newObj = locationsService.create(id);
        User user = SessionUtil.getCurrentUserBySession(session);
        newObj.setSuperior(user.getPerson().getPersonName());
        newObj.setStatus("1");
        modelMap.put("locations", newObj);
        lineList = lineService.findByStatus("1");
        stationList = stationService.findByStatus("1");
        List<Locations> locationsList = locationsService.findAll();
        modelMap.put("locationsList", locationsList);
        modelMap.put("lineList", lineList);
        modelMap.put("stationList", stationList);
        return "/location/create";
    }


    /**
     * @param locations
     * @return 保存位置信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Locations save(Locations locations) {
        return locationsService.save(locations);

    }


    /**
     * @param id
     * @return 根据id查询
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Locations findById(@PathVariable("id") Long id) {
        return locationsService.findById(id);

    }


    /**
     * @param id
     * @return删除位置信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        Locations locations = locationsService.findById(id);
        return locationsService.delete(locations);
    }


    /**
     * @param pid 上级节点
     * @return 根据上级节点id查询
     */
    @RequestMapping(value = "/findNodeByParentId/{pid}")
    @ResponseBody
    public List<Locations> findNodeByParentId(@PathVariable("pid") Long pid) {
        List<Locations> locationsList = locationsService.findByParentId(pid);
        return locationsList;
    }


    /**
     * @return 根据上级节点id查询
     */
    @RequestMapping(value = "/loadReportForm")
    public String loadReportForm() {
        return "/location/locationReport2";
    }

}
