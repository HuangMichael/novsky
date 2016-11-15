package com.linkbit.beidou.controller.matcost;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.budget.VbudgetBill;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.matCost.MatCostService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/matCost")
public class MatCostController extends BaseController {

    @Autowired
    ResourceService resourceService;

    @Autowired
    MatCostService matCostService;

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
        Page<MatCost> page = matCostService.findByMatName(searchPhrase, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }


    /**
     * 分页查询
     *
     * @param ecType
     * @param locName
     * @param ecName
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public MyPage search(
            @RequestParam(value = "ecType", required = false) String ecType,
            @RequestParam(value = "locName", required = false) String locName,
            @RequestParam(value = "ecName", required = false) String ecName,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount) {

        Page<MatCost> page = matCostService.findByCondition(ecType, locName, ecName, new PageRequest(current - 1, rowCount.intValue()));

        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());

        return myPage;

    }

    /**
     * @return 查询我的位置信息
     */
    @RequestMapping(value = "/findMyLocs", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findMyLocs() {
        return matCostService.findMyLocs();
    }

    /**
     * @return 载入页面
     */
    @RequestMapping(value = "/loadPage", method = RequestMethod.GET)
    public String loadPage() {
        return "matCost/matCostList";
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
        List<MatCost> dataList = matCostService.findAll();
        matCostService.setDataList(dataList);
        matCostService.exportExcel(request, response, docName, titles, colNames);
    }


}
