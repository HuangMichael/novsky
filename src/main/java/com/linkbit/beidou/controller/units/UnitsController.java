package com.linkbit.beidou.controller.units;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.units.Units;
import com.linkbit.beidou.service.unit.UnitService;
import com.linkbit.beidou.utils.StringUtils;
import com.linkbit.beidou.utils.export.docType.ExcelDoc;
import com.linkbit.beidou.utils.export.exporter.DataExport;
import com.linkbit.beidou.utils.export.exporter.ExcelDataExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2016/03/23 0023.
 * 外委单位控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/units")
public class UnitsController extends BaseController {

    @Autowired
    OutsourcingUnitRepository outsourcingUnitRepository;
    @Autowired
    UnitService unitService;


    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {
        super.list(httpSession, modelMap);
        return "/units/list";
    }



    /**
     * 分页查询
     *
     * @param current      当前页
     * @param rowCount     每页条数
     * @param searchPhrase 查询关键字
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(@RequestParam(value = "current", defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Page<Units> page = unitService.findByDescriptionContains(searchPhrase, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }



    /**
     * @return 查询 所有的外委单位信息
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Units> findAll() {
        List<Units> outsourcingUnitList = unitService.findAll();
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
    public Units save(Units units) {
        units.setStatus("1");
        units = outsourcingUnitRepository.save(units);
        return units;
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
        Units units = new Units();
        units.setUnitNo(unitNo);
        units.setDescription(description);
        units.setLinkman(linkman);
        units.setTelephone(telephone);
        units.setWorkDays(workDays);
        units.setStatus("1");
        units = outsourcingUnitRepository.save(units);
        return unitService.saveLink(units, eqClassId);
    }


    /**
     * @param id 根据id删除设备信息
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        Units unit = null;
        if (id != null) {
            unit = unitService.findById(id);
        }
        return unitService.delete(unit);

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
        return unitService.unitNoExists(unitNo);
    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames) {
        List<Units> dataList = unitService.findByDescriptionContains(param);
        unitService.setDataList(dataList);
        unitService.exportExcel(request, response, docName, titles, colNames);
    }


}
