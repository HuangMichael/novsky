package com.linkbit.beidou.controller.units;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.domain.outsourcingUnit.Units;
import com.linkbit.beidou.service.unit.UnitService;
import com.linkbit.beidou.utils.StringUtils;
import com.linkbit.beidou.utils.export.docType.ExcelDoc;
import com.linkbit.beidou.utils.export.exporter.DataExport;
import com.linkbit.beidou.utils.export.exporter.ExcelDataExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 * 外委单位控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/unit")
public class UnitsController extends BaseController {

    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;
    @Autowired
    UnitService outsourcingUnitService;


    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {
        super.list(httpSession, modelMap);
        return "/units/list";
    }

    /**
     * @return 查询 所有的外委单位信息
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Units> findAll() {
        List<Units> outsourcingUnitList = outsourcingUnitService.findAll();
        return outsourcingUnitList;
    }


    /**
     * @param unitId   外委单位信息
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail/{unitId}", method = RequestMethod.GET)
    public String detail(@PathVariable("unitId") Long unitId, ModelMap modelMap) {
        Units unit = outsourcingUnitRepository.findById(unitId);
        if (unit != null) {
            modelMap.put("unit", unit);
        }
        return "/units/detail";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Units save(Units outsourcingUnit) {
        outsourcingUnit.setStatus("1");
        outsourcingUnit = outsourcingUnitRepository.save(outsourcingUnit);
        return outsourcingUnit;
    }


    @RequestMapping(value = "/saveLink", method = RequestMethod.POST)
    @ResponseBody
    public List<Units> saveLink(@RequestParam("unitNo") String unitNo,
                                @RequestParam("description") String description,
                                @RequestParam("linkman") String linkman,
                                @RequestParam("telephone") String telephone,
                                @RequestParam("eqClassId") Long eqClassId,
                                @RequestParam("workDays") String workDays
    ) {
        Units outsourcingUnit = new Units();
        outsourcingUnit.setUnitNo(unitNo);
        outsourcingUnit.setDescription(description);
        outsourcingUnit.setLinkman(linkman);
        outsourcingUnit.setTelephone(telephone);
        outsourcingUnit.setWorkDays(workDays);
        outsourcingUnit.setStatus("1");
        outsourcingUnit = outsourcingUnitRepository.save(outsourcingUnit);
        return outsourcingUnitService.saveLink(outsourcingUnit, eqClassId);
    }


    /**
     * @param id 根据id删除设备信息
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        Units unit = null;
        if (id != null) {
            unit = outsourcingUnitService.findById(id);
        }
        return outsourcingUnitService.delete(unit);

    }


    @RequestMapping(value = "/downloadContract/{contractId}", method = RequestMethod.GET)
    public String downloadContract(@PathVariable("contractId") Long contractId, ModelMap modelMap) {
        return "/docs/contracts/abc.doc";
    }


    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Units findById(@PathVariable("id") Long id) {
        return outsourcingUnitRepository.findById(id);
    }

    /**
     * @param status 状态
     * @return 根据状态查询外委单位信息
     */
    @RequestMapping(value = "/findByStatus/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<Units> findByStatus(@PathVariable("status") String status) {
        return outsourcingUnitRepository.findByStatus(status);
    }


    @RequestMapping(value = "/loadPageByUrl/{pageUrl}/{uid}", method = RequestMethod.GET)
    public String loadPageByUrl(@PathVariable("pageUrl") String pageUrl, @PathVariable("uid") Long uid, ModelMap modelMap) {
        Units unit = outsourcingUnitRepository.findById(uid);
        modelMap.put("unit", unit);
        return "/units/" + pageUrl;
    }


    /**
     * @param unitNo 单位编号
     * @return 查询单位编号是否存在
     */
    @RequestMapping(value = "/checkUnitCodeExists/{unitNo}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkUnitCodeExists(@PathVariable("unitNo") String unitNo) {
        return outsourcingUnitService.unitNoExists(unitNo);
    }

    /**
     * @param request
     * @param response
     * @param param
     * @param docName
     * @param titles
     * @param colNames
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames) {
        List<String> titleList = StringUtils.removeNullValue(titles);
        List<String> colNameList = StringUtils.removeNullValue(colNames);
        List<Units> unitList = outsourcingUnitService.findByDescriptionContains(param);
        try {
            DataExport dataExport = new ExcelDataExporter();
            dataExport.export(new ExcelDoc(), request, response, titleList, colNameList, unitList, docName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
