package com.subway.service.locations;

import com.subway.dao.line.LineRepository;
import com.subway.dao.line.StationRepository;
import com.subway.dao.locations.LocationsRepository;
import com.subway.dao.locations.VlocationsRepository;
import com.subway.domain.line.Line;
import com.subway.domain.line.Station;
import com.subway.domain.locations.Locations;
import com.subway.domain.locations.Vlocations;
import com.subway.object.ReturnObject;
import com.subway.service.app.BaseService;
import com.subway.service.commonData.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class LocationsService extends BaseService {


    @Autowired
    LocationsRepository locationsRepository;

    @Autowired
    VlocationsRepository vlocationsRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    StationRepository stationRepository;

    /**
     * 设置位置编码
     */
    public String getLocationsNo(Locations locations) {
        List<Locations> locationsList = locationsRepository.findByParentOrderByLocationDesc(locations.getId());
        String locationNo = "";
        if (!locationsList.isEmpty()) {
            Locations youngestChild = locationsList.get(0);
            if (youngestChild != null) {
                String location = youngestChild.getLocation();
                String index = location.substring(location.length() - 2, location.length());
                if (index != null && !index.equals("")) {
                    long n = (Long.parseLong(index) + 1);
                    locationNo = locations.getLocation() + ((n < 10) ? "0" + n : n);
                }
            }
        } else {
            locationNo = locations.getLocation() + "01";
        }
        return locationNo;
    }

    /**
     * @param locations 保存位置信息
     * @return
     */
    public Locations save(Locations locations) {
        return locationsRepository.save(locations);
    }


    public List<Locations> findAll() {
        return locationsRepository.findAll();
    }


    /**
     * @param location 位置编号
     * @return 当前用户分配的所有位置节点
     */
    public List<Object> findTree(String location) {
        List<Object> objectList = null;

        if (location != null && !location.equals("")) {
            objectList = locationsRepository.findTree(location);
        }
        return objectList;
    }


    /**
     * @param locLevel 节点级别
     * @return 查询节点级别小于locLevel的记录
     */
    public List<Locations> findByLocLevelLessThan(Long locLevel) {
        return locationsRepository.findByLocLevelLessThan(locLevel);
    }

    /**
     * @param id 根据ID查询位置
     * @return
     */
    public Locations findById(Long id) {
        return locationsRepository.findById(id);
    }

    /**
     * @param location
     * @return
     */
    public List<Locations> findByLocation(String location) {
        return locationsRepository.findByLocation(location);
    }


    /**
     * @param id 根据ID查询位置
     * @return
     */
    public List<Locations> findByParentId(Long id) {
        Locations locations = locationsRepository.findById(id);
        return locationsRepository.findByParent(id);
    }

    /**
     * @param locations 位置信息
     * @return 删除位置信息
     */
    public ReturnObject delete(Locations locations) {
        //有子节点不能删除
        Long id = locations.getId();
        ReturnObject returnObject = new ReturnObject();
        boolean hasChild = !locationsRepository.findByParent(locations.getId()).isEmpty();
        if (hasChild) {
            returnObject.setResult(false);
            returnObject.setResultDesc("该位置下有位置信息不能删除!");
        } else {
            try {
                locationsRepository.delete(locations);
                //再查询一次查看是否删除
                Locations l = locationsRepository.findById(id);
                if (l == null) {
                    returnObject.setResult(true);
                    returnObject.setResultDesc("位置信息删除成功!");

                }
            } catch (Exception e) {
                e.printStackTrace();
                returnObject.setResult(false);
                returnObject.setResultDesc("位置信息有关联数据，无法删除，请联系管理员!");

            }

        }
        return returnObject;
    }


    /**
     * 新建位置
     *
     * @param parentId 上级位置
     * @return 如果有上级根据上级生成对象  如果没有将其当做根节点
     */
    public Locations create(Long parentId) {

        //创建之时  如果loc_level 为3站时 保存 3以下位置保存line_id 和station_id
        Locations newObj = new Locations();
        if (parentId != null) {
            Locations parent = locationsRepository.findById(parentId);
            newObj.setLocation(getLocationsNo(parent));  //编号不自动生成
            newObj.setParent(parent.getId());
            Long level = 0l;
            if (parent.getLocLevel() != null) {
                level = parent.getLocLevel();
            }
            newObj.setLocLevel(level + 1);
    /*        if (newObj.getLocLevel() >= 2 && newObj.getLocLevel() <= 3) {
                newObj.setLine(parent.getLine());
            }
            if (newObj.getLocLevel() > 3) {
                newObj.setLine(parent.getLine());
                newObj.setStation(pa);
            }*/

        } else {
            newObj.setLocation("01");
        }
        return newObj;

    }


    /**
     * @param location
     * @return 根据位置编码模糊查询
     */
    public List<Locations> findByLocationStartingWithAndStatus(String location, String status) {

        return locationsRepository.findByLocationStartingWithAndStatus(location, status);
    }


    /**
     * @param location
     * @return 根据位置编码模糊查询
     */
    public List<Vlocations> findByLocationStartingWithAndStatus(String location) {

        return vlocationsRepository.findByLocationStartingWith(location);
    }


    /**
     * @param cid    位置编号id
     * @param locStr 位置名称字符串
     * @return 导入设备分类
     */

    public ReturnObject importLoc(Long cid, String locStr, String split) {
        Locations locations = locationsRepository.findById(cid);
        int records = 0;

        List<Locations> locationsList = null;
        Locations newLoc;
        //根据分隔符分割
        String[] locArray = locStr.split(split);
        String location = "";
        for (String classDesc : locArray) {
            //查询是否已经存在 类型和名称确定唯一一个设备类型
            classDesc=classDesc.trim();
            locationsList = locationsRepository.findByParentAndDescription(cid, classDesc);
            if (locationsList.isEmpty()) {
                newLoc = new Locations();
                location = getLocationsNo(locations);
                newLoc.setDescription(classDesc);
                newLoc.setLocation(location);
                newLoc.setLocLevel(locations.getLocLevel() + 1);
                newLoc.setHasChild("0");
                newLoc.setParent(locations.getId());
                newLoc.setStatus("1");
                locationsRepository.save(newLoc);
                records++;
            }
            //如果不存在，保存
        }

        return commonDataService.getReturnType(records != 0, records + "条位置信息导入成功！", "位置信息导入失败！");
    }


    /**
     * @param cid 位置编号id
     * @return 同步设备位置线路和车站数据
     */

    public ReturnObject sysnLoc(Long cid) {
        //根据id查询设备位置
        Locations locations = locationsRepository.findById(cid);
        String location = locations.getLocation();
        //根据位置查询出子位置
        long records = 0;
        List<Locations> subLocations = locationsRepository.findByLocationStartingWith(location);
        Line line = null;
        Station station = null;
        //为每个子位置设置车站和线路
        for (Locations child : subLocations) {
            location = child.getLocation();
            //查询该位置下的所有位置 设置line
            if (child.getLocLevel() == 2) {
                line = lineRepository.findByLineNo(location.substring(0, 4)).get(0);
                child.setLine(line);
                //设置line
            } else if (child.getLocLevel() >= 3) {
                //设置station
                line = lineRepository.findByLineNo(location.substring(0, 4)).get(0);
                station = stationRepository.findByStationNo(location.substring(0, 6)).get(0);
                child.setLine(line);
                child.setStation(station);
            }
            child = locationsRepository.save(child);
        }
        records = subLocations.size();
        //查询出station
        return commonDataService.getReturnType(records != 0, records + "条位置信息更新成功！", "位置信息更新失败！");
    }

}

