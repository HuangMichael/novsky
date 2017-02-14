package com.subway.service.locations;

import com.subway.dao.locations.LocationsRepository;
import com.subway.dao.locations.VlocationsRepository;
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

        System.out.println("locationNo------------------------" + locationNo);
        return locationNo;
    }

    /**
     * @param locations 保存位置信息
     * @return
     */
    public Locations save(Locations locations) {

        System.out.println("locations------------------------" + locations.toString());


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

}

