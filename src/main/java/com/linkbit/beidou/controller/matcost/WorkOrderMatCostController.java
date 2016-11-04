package com.linkbit.beidou.controller.matcost;


import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.equipments.VEqAddBill;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.matCost.WorkOrderMatCost;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.service.workOrderMatCost.WorkOrderMatCostService;
import com.linkbit.beidou.utils.DateUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/workOrderMatCost")
public class WorkOrderMatCostController {

    @Autowired
    ResourceService resourceService;

    @Autowired
    WorkOrderMatCostService workOrderMatCostService;

    @Autowired
    CommonDataService commonDataService;

    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {
        String controllerName = this.getClass().getSimpleName().split("Controller")[0];
        List<VRoleAuthView> appMenus = resourceService.findAppMenusByController(httpSession, controllerName.toUpperCase());
        modelMap.put("appMenus", appMenus);
        return "/workOrderMatCost/list";
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
        Page<WorkOrderMatCost> page = workOrderMatCostService.findByCondition(searchPhrase, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }


    /**
     * @param file
     * @param request
     * @return 上传文件
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        return workOrderMatCostService.upload(file, request);
    }


    /**
     * @param filePath 文件路径
     * @return 导入excel返回结果
     * @throws Exception
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject importExcel(@RequestParam("filePath") String filePath) throws Exception {
        return workOrderMatCostService.importExcel(filePath);
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "redirect:/docs/wocost/mb.xls";
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
        List<WorkOrderMatCost> dataList = workOrderMatCostService.findByCondition(param);
        workOrderMatCostService.setDataList(dataList);
        workOrderMatCostService.exportExcel(request, response, docName, titles, colNames);
    }

}
