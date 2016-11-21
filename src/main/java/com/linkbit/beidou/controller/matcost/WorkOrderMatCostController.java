package com.linkbit.beidou.controller.matcost;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.equipments.VEqAddBill;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.matCost.WorkOrderMatCost;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.service.workOrderMatCost.WorkOrderMatCostSearchService;
import com.linkbit.beidou.service.workOrderMatCost.WorkOrderMatCostService;
import com.linkbit.beidou.utils.DateUtils;
import com.linkbit.beidou.utils.PageUtils;
import com.linkbit.beidou.utils.StringUtils;
import com.linkbit.beidou.utils.UploadUtil;
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
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/workOrderMatCost")
public class WorkOrderMatCostController extends BaseController {

    @Autowired
    ResourceService resourceService;

    @Autowired
    WorkOrderMatCostService workOrderMatCostService;


    @Autowired
    WorkOrderMatCostSearchService workOrderMatCostSearchService;


    @Autowired
    CommonDataService commonDataService;


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

        return new PageUtils().searchByService(workOrderMatCostSearchService, searchPhrase, 3, current, rowCount);
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


    @RequestMapping(value = "/download", method = {RequestMethod.GET})
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getRealPath("/");
        response.setContentType("application/vnd.ms-excel");
        String fileName = "工单物资消耗模板.xls";
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String remotePath = path + "docs\\wo\\" + fileName;
        BufferedInputStream in;
        BufferedOutputStream out;
        try {
            in = new BufferedInputStream(new FileInputStream(remotePath));
            out = new BufferedOutputStream(response.getOutputStream());
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<WorkOrderMatCost> dataList = workOrderMatCostService.findByCondition(param, 3);
        workOrderMatCostService.setDataList(dataList);
        workOrderMatCostService.exportExcel(request, response, docName, titles, colNames);
    }


}
