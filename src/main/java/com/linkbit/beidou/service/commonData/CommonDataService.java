package com.linkbit.beidou.service.commonData;

import com.linkbit.beidou.dao.equipments.EquipmentsClassificationRepository;
import com.linkbit.beidou.dao.equipments.VeqClassRepository;
import com.linkbit.beidou.dao.locations.LocationsRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.equipments.VeqClass;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.locations.Vlocations;
import com.linkbit.beidou.object.ListObject;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class CommonDataService extends BaseService {

    @Autowired
    LocationsRepository locationsRepository;

    @Autowired
    VlocationsRepository vlocationsRepository;

    @Autowired
    VeqClassRepository veqClassRepository;

    @Autowired
    EquipmentsClassificationRepository equipmentsClassificationRepository;

    /**
     * @param location 位置编号
     * @return 查询我的下属位置信息
     * 先从session中找  如果失败再做查询
     */
    public List<Locations> findMyLocation(String location, HttpSession httpSession) {
        List<Locations> locationsList = null;
        Object object = httpSession.getAttribute("locationsList");
        if (object != null) {
            locationsList = (ArrayList<Locations>) object;
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询位置信息");
        } else {
            if (location != null && !location.equals("")) {
                locationsList = locationsRepository.findByLocationStartingWith(location);
                log.info(this.getClass().getCanonicalName() + "------------从缓存中查询位置信息");
            }
        }
        return locationsList;
    }


    /**
     * @param location    位置编号
     * @param httpSession 查询位置我的视图信息
     * @return 查询我的下属位置信息
     * 先从session中找  如果失败再做查询
     */
    public List<Vlocations> findMyVLocation(String location, HttpSession httpSession) {
        List<Vlocations> locationsList = null;
        Object object = httpSession.getAttribute("locationsList");
        if (object != null) {
            locationsList = (ArrayList<Vlocations>) object;
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询位置信息");
        } else {
            if (location != null && !location.equals("")) {
                locationsList = vlocationsRepository.findByLocationStartingWith(location);
                log.info(this.getClass().getCanonicalName() + "------------从缓存中查询位置信息");
            }
        }
        return locationsList;
    }


    /**
     * @param httpSession
     * @return 查询设备种类信息
     */
    public List<EquipmentsClassification> findEquipmentsClassification(HttpSession httpSession) {
        List<EquipmentsClassification> equipmentsClassificationList = null;
        Object object = httpSession.getAttribute("equipmentsClassificationList");
        if (object != null) {
            equipmentsClassificationList = (ArrayList<EquipmentsClassification>) object;
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询设备种类");

        } else {
            equipmentsClassificationList = equipmentsClassificationRepository.findAll();
            log.info(this.getClass().getCanonicalName() + "------------从数据库中查询设备种类");
            httpSession.setAttribute("equipmentsClassificationList", equipmentsClassificationList);
            log.info(this.getClass().getCanonicalName() + "------------设备种类放入缓存");
        }
        return equipmentsClassificationList;


    }


    /**
     * @param httpSession
     * @return 查询设备种类信息
     */
    public List<VeqClass> findVeqClass(HttpSession httpSession) {
        List<VeqClass> eqClassList = null;
        Object object = httpSession.getAttribute("eqClassList");
        if (object != null) {
            eqClassList = (ArrayList<VeqClass>) object;
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询设备种类视图");
        } else {
            eqClassList = veqClassRepository.findAll();
            log.info(this.getClass().getCanonicalName() + "------------从数据库中查询设备种类视图");
            httpSession.setAttribute("eqClassList", eqClassList);
            log.info(this.getClass().getCanonicalName() + "------------设备种类视图放入缓存");
        }
        return eqClassList;


    }



    /**
     * @param httpSession
     * @return  (0:停用 1:投用 2报废)
     */
    public List<ListObject> getEqStatus(HttpSession httpSession) {
        List<ListObject> eqStatusList = new ArrayList<ListObject>();
        Object object = httpSession.getAttribute("eqStatusList");
        if (object != null) {
            eqStatusList = (ArrayList<ListObject>) httpSession.getAttribute("eqStatusList");
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询设备状态");
        } else {
            log.info(this.getClass().getCanonicalName() + "------------从数据库中查询设备状态");
            eqStatusList.add(new ListObject("0", "停用"));
            eqStatusList.add(new ListObject("1", "投用"));
            eqStatusList.add(new ListObject("2", "报废"));
            log.info(this.getClass().getCanonicalName() + "------------设备状态放入缓存");
            httpSession.setAttribute("eqStatusList", eqStatusList);
        }
        return eqStatusList;

    }





    /**
     * @param httpSession
     * @return 获取运行状态 (0:停止 1:运行)
     */
    public List<ListObject> getRunningStatus(HttpSession httpSession) {
        List<ListObject> eqRunStatusList = new ArrayList<ListObject>();
        Object object = httpSession.getAttribute("eqRunStatusList");
        if (object != null) {
            eqRunStatusList = (ArrayList<ListObject>) httpSession.getAttribute("eqRunStatusList");
            log.info(this.getClass().getCanonicalName() + "------------从缓存中查询设备运行状态");
        } else {
            log.info(this.getClass().getCanonicalName() + "------------从数据库中查询设备运行状态");
            eqRunStatusList.add(new ListObject("0", "停止"));
            eqRunStatusList.add(new ListObject("1", "运行"));
            log.info(this.getClass().getCanonicalName() + "------------设备运行状态放入缓存");
            httpSession.setAttribute("eqRunStatusList", eqRunStatusList);
        }
        return eqRunStatusList;

    }

}
