package com.subway.controller.locations;

import com.subway.controller.common.BaseController;
import com.subway.dao.equipments.VequipmentsRepository;
import com.subway.dao.locations.VlocationsRepository;
import com.subway.domain.equipments.Equipments;
import com.subway.domain.equipments.Vequipments;
import com.subway.domain.locations.Locations;
import com.subway.domain.locations.Vlocations;
import com.subway.domain.user.User;
import com.subway.object.ReturnObject;
import com.subway.service.app.ResourceService;
import com.subway.service.commonData.CommonDataService;
import com.subway.service.equipments.EquipmentAccountService;
import com.subway.service.locations.LocationsService;
import com.subway.utils.SessionUtil;
import com.subway.utils.importer.tool.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
public class LocationController extends BaseController {

    @Autowired
    LocationsService locationsService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    VequipmentsRepository vequipmentsRepository;
    @Autowired
    CommonDataService commonDataService;


    @Autowired
    ImportService importService;

    @Autowired
    EquipmentAccountService equipmentAccountService;

    @Autowired
    VlocationsRepository vlocationsRepository;


    /**
     * @param id
     * @return 根据ID显示位置信息 显示明细
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        String url = "/location";
        Locations object = locationsService.findById(id);
        if (id != 0) {
            url += "/locEqList";

        }
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
        if (user.getVlocations().getLocation() != null && !user.getVlocations().getLocation().equals("")) {
            objectList = locationsService.findTree(user.getVlocations().getLocation() + "%");
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
     * @param session
     * @return 新建位置信息
     */
    @RequestMapping(value = "/create/{id}")
    @ResponseBody
    public Locations create(@PathVariable("id") Long id, HttpSession session) {
        Locations location = locationsService.create(id);
        location.setStatus("1");
        return location;
    }


    /**
     * @param locations
     * @return 保存位置信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(Locations locations) {
        locations = locationsService.save(locations);
        return commonDataService.getReturnType(locations != null, "位置信息保存成功!", "位置信息保存失败!");

    }

    /**
     * @param id
     * @return 根据id查询
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
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


    /**
     * @return 根据上级节点id查询
     */
    @RequestMapping(value = "/importData/{id}")
    public ReturnObject importData(@PathVariable("id") Long locationId) {

        String filePath = "d://设备信息.xls";
        List<String> columnList = new ArrayList<String>();
        columnList.add("EqCode");
        columnList.add("Description");
        columnList.add("Manager");
        String packageName = "Equipments";
        List<Equipments> equipmentsList = null;
        try {
            equipmentsList = importService.assembleList(filePath, packageName, columnList);
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "数据导入成功!", "数据路径有误!");
        }

        Locations locations = locationsService.findById(locationId);
        for (Equipments equipments : equipmentsList) {
            equipments.setLocations(locations);
            equipments.setLocation(locations.getLocation());
            equipments.setVlocations(vlocationsRepository.findById(locationId));
            equipments.setStatus("1");
        }
        importService.importData(equipmentAccountService, equipmentsList);
        return commonDataService.getReturnType(true, "数据导入成功!", "数据导入失败!");
    }


    /**
     * @param id
     * @return 根据位置id查询位置具体描述
     */
    @RequestMapping(value = "/findLocNameById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Vlocations findLocNameById(@PathVariable("id") Long id) {
        Vlocations vlocations = vlocationsRepository.findById(id);
        return vlocations;
    }


    /**
     * @param lid      位置ID
     * @param locStr
     * @param split    分隔符
     * @return 返回导入成功失败标识
     */
    @RequestMapping(value = "/importLoc", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject importLoc(@RequestParam("lid") Long lid, @RequestParam("locStr") String locStr, @RequestParam("split") String split) {

        System.out.println("lid---------------"+lid);
        System.out.println("locStr---------------"+locStr);
        System.out.println("split---------------"+split);
        return locationsService.importLoc(lid,locStr,split);
    }


}
