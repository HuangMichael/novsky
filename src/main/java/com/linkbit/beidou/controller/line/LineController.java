package com.linkbit.beidou.controller.line;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.service.line.LineSearchService;
import com.linkbit.beidou.service.line.LineService;
import com.linkbit.beidou.utils.PageUtils;
import com.linkbit.beidou.utils.SessionUtil;
import com.linkbit.beidou.utils.StringUtils;
import com.linkbit.beidou.utils.export.docType.ExcelDoc;
import com.linkbit.beidou.utils.export.exporter.DataExport;
import com.linkbit.beidou.utils.export.exporter.ExcelDataExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 * 线路控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/line")
public class LineController extends BaseController {

    @Autowired
    LineService lineService;
    @Autowired
    CommonDataService commonDataService;

    @Autowired
    ResourceService resourceService;
    @Autowired
    LineSearchService lineSearchService;


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
        return new PageUtils().searchByService(lineSearchService, searchPhrase, 2, current, rowCount);
    }


    @RequestMapping(value = "/lines", method = {RequestMethod.GET})
    @ResponseBody
    public List<Line> findAllUseFulLines() {
        return lineService.findByStatus("1");
    }


    /**
     * 根据所有线路信息
     */
    @RequestMapping(value = "/findByStatus")
    @ResponseBody
    public List<Line> findByStatus() {
        List<Line> lineList = lineService.findByStatus("1");
        return lineList;
    }

    /**
     * @param id 根据id查询车站信息
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Line findById(@PathVariable("id") long id) {
        Line line = lineService.findById(id);
        return line;
    }


    @RequestMapping(value = "/create")
    public String create() {
        return "/line/create";
    }


    /**
     * 保存线路信息
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public Line save(Line line) {
        return lineService.save(line);
    }


    /**
     * 保存角色信息
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public Line update(@RequestParam("lineId") Long lineId,
                       @RequestParam("lineNo") String lineNo,
                       @RequestParam("sortNo") Long sortNo,
                       @RequestParam("description") String description) {
        Line line = lineService.findById(lineId);
        line.setDescription(description);
        line.setSortNo(sortNo);
        line.setLineNo(lineNo);
        return lineService.save(line);
    }


    /**
     * @return 查询所有的线路
     */
    @RequestMapping(value = "/findAllLines")
    @ResponseBody
    public List<Line> findAllLines() {
        List<Line> lineList = lineService.findAll();
        return lineList;
    }


    /**
     * @param id 根据id删除设备信息
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        Line line = null;
        if (id != null) {
            line = lineService.findById(id);
        }
        return lineService.delete(line);

    }


    /**
     * @return 查询所有的线路
     */
    @RequestMapping(value = "/findLines")
    @ResponseBody
    public List<Line> findLines() {
        List<Line> lineList = lineService.findLines();
        return lineList;
    }


    /**
     * @return 查询所有的线路
     */
    @RequestMapping(value = "/findSegs")
    @ResponseBody
    public List<Line> findSegs() {
        List<Line> lineList = lineService.findSegs();
        return lineList;
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
        List<Line> dataList = lineSearchService.findByConditions(param,2);
        lineSearchService.setDataList(dataList);
        lineSearchService.exportExcel(request, response, docName, titles, colNames);
    }
}
