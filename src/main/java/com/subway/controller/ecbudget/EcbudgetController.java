package com.subway.controller.ecbudget;


import com.subway.controller.common.BaseController;
import com.subway.domain.EcBudget.EcBudgetBill;
import com.subway.domain.EcBudget.VEcBudgetBill;
import com.subway.domain.app.MyPage;
import com.subway.object.ReturnObject;
import com.subway.service.app.ResourceService;
import com.subway.service.budge.EcBudgeSearchService;
import com.subway.service.budge.EcBudgeService;
import com.subway.service.commonData.CommonDataService;
import com.subway.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 低值易耗品采购申请
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/ecbudget")
public class EcbudgetController extends BaseController {

    @Autowired
    EcBudgeService ecBudgeService;

    @Autowired
    EcBudgeSearchService ecBudgeSearchService;

    @Autowired
    ResourceService resourceService;
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
    public MyPage data(HttpServletRequest request, @RequestParam(value = "current", defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortService(ecBudgeSearchService, searchPhrase, 4, current, rowCount, pageable);
    }


    /**
     * @param id 根据id查询
     * @return
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EcBudgetBill findById(@PathVariable("id") Long id) {
        return ecBudgeService.findById(id);
    }


    /**
     * @param budgetBill 保存或者更新低值易耗品采购单
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(EcBudgetBill budgetBill) {
        EcBudgetBill budgetObj;
        String operation = "保存";
        if (budgetBill.getId() != null) {
            operation = "更新";
        }
        budgetObj = ecBudgeService.save(budgetBill);
        return commonDataService.getReturnType(budgetObj != null, "低值易耗品采购申请单" + operation + "成功!", "低值易耗品采购申请单" + operation + "失败!");
    }


    /**
     * @param id 根据id查询
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        return ecBudgeService.delete(id);
    }


    /**
     * @return 查询所有的id集合
     */
    @RequestMapping(value = "/findAllIds", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> findAllIds() {
        return ecBudgeService.findAllIds();
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
        List<VEcBudgetBill> dataList = ecBudgeSearchService.findByConditions(param, 4);
        ecBudgeService.setDataList(dataList);
        ecBudgeService.exportExcel(request, response, docName, titles, colNames);
    }

}
