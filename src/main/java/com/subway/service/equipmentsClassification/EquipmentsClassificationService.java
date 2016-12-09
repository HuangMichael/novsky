package com.subway.service.equipmentsClassification;

import com.subway.dao.equipments.EquipmentsClassificationRepository;
import com.subway.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.subway.domain.equipments.EquipmentsClassification;
import com.subway.domain.role.Role;
import com.subway.domain.units.Units;
import com.subway.domain.user.User;
import com.subway.object.ReturnObject;
import com.subway.service.app.BaseService;
import com.subway.service.commonData.CommonDataService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class EquipmentsClassificationService extends BaseService {

    @Autowired
    EquipmentsClassificationRepository equipmentsClassificationRepository;

    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * 根据上级设备种类生成子类编号
     */
    public String getCodeByParent(EquipmentsClassification equipmentClassification) {
        String code = "";
        if (equipmentClassification != null) {
            code += equipmentClassification.getClassId();
            int childrenSize = equipmentClassification.getClassificationList().size();
            for (int i = 1; i < 3 - (childrenSize + "").length(); i++) {
                code += "0";
            }
            code += (childrenSize + 1);
        } else {
            code = "001";
        }
        return code;
    }

    /**
     * 新建设备分类
     */
    public EquipmentsClassification create(Long parentId) {
        EquipmentsClassification parent = equipmentsClassificationRepository.findById(parentId);
        EquipmentsClassification newObj = new EquipmentsClassification();
        newObj.setClassId(this.getCodeByParent(parent));
        newObj.setParent(parent);
        return newObj;

    }


    /**
     * 新建设备分类
     */
    public List<EquipmentsClassification> findAll() {
        List<EquipmentsClassification> equipmentsClassificationList = equipmentsClassificationRepository.findAll();
        return equipmentsClassificationList;
    }

    /**
     * 保存设备分类
     */
    public EquipmentsClassification save(EquipmentsClassification equipmentsClassification) {
        return equipmentsClassificationRepository.save(equipmentsClassification);
    }

    /**
     * 根据编号查询种类
     */

    public EquipmentsClassification findById(Long id) {
        return equipmentsClassificationRepository.findById(id);
    }

    /**
     * 删除设备种类
     */

    public void delete(EquipmentsClassification equipmentsClassification) {
        equipmentsClassificationRepository.delete(equipmentsClassification);
    }


    public List<Long> getUnitsByEqClassId(Long cid) {
        EquipmentsClassification equipmentsClassification;
        List<Long> idList = new ArrayList<Long>();
        if (cid != null) {
            equipmentsClassification = equipmentsClassificationRepository.findById(cid);
            List<Units> unitList = equipmentsClassification.getUnitSet();
            for (Units unit : unitList) {
                idList.add(unit.getId());
            }

            if (idList.isEmpty()) {
                idList.add(0l);
            }
        }
        return idList;
    }


    /**
     * 查询出不在当前设备分类的外委单位
     *
     * @param cid
     * @return
     */

    public List<Object> findUnitsNotInEqclass(Long cid) {
        return outsourcingUnitRepository.findUnitListByEqClassIdNotEq(cid);
    }


    /**
     * @param cid
     * @param unitsIdStr
     * @return 添加外委单位
     */
    public ReturnObject addUnits(Long cid, String unitsIdStr) {
        EquipmentsClassification equipmentsClassification = equipmentsClassificationRepository.findById(cid);
        if (unitsIdStr != null && !unitsIdStr.equals("")) {
            String[] ids = unitsIdStr.split(",");
            List<Units> unitsList = equipmentsClassification.getUnitSet();
            for (String id : ids) {
                Units units = outsourcingUnitRepository.findById(Long.parseLong(id));
                if (!unitsList.contains(units)) {
                    unitsList.add(units);
                }
            }
            equipmentsClassification.setUnitSet(unitsList);
            equipmentsClassification = equipmentsClassificationRepository.save(equipmentsClassification);
        }
        return commonDataService.getReturnType(equipmentsClassification != null, "设备分类关联外委单位成功！", "设备分类关联外委单位失败！");
    }
}
