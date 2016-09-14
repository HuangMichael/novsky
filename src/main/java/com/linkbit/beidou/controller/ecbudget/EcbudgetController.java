package com.linkbit.beidou.controller.ecbudget;


import com.linkbit.beidou.domain.EcBudget.EcBudgetBill;
import com.linkbit.beidou.domain.EcBudget.VEcBudgetBill;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.budget.BudgetBill;
import com.linkbit.beidou.domain.budget.VbudgetBill;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.budge.BudgeService;
import com.linkbit.beidou.service.budge.EcBudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 采购申请
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/ecbudget")
public class EcbudgetController {

    @Autowired
    EcBudgeService ecBudgeService;
    @Autowired
    ResourceService resourceService;


    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public List<EcBudgetBill> data() {
        return ecBudgeService.findAll();
    }

    /**
     * 显示所有的报修车列表信息
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(@RequestParam(value = "current", defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount) {
        Page<VEcBudgetBill> page = ecBudgeService.findAllV(new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(ecBudgeService.findAll().size());
        return myPage;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpSession httpSession, ModelMap modelMap) {
        String controllerName = this.getClass().getSimpleName().split("Controller")[0];
        List<VRoleAuthView> appMenus = resourceService.findAppMenusByController(httpSession, controllerName.toUpperCase());
        modelMap.put("appMenus", appMenus);
        return "/ecbudget/list";
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
     * @param budgetBill 采购单
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(EcBudgetBill budgetBill, HttpSession httpSession) {
        ReturnObject returnObject = new ReturnObject();
        EcBudgetBill budgetObj;
        String operation = "保存";
        String result = "成功";
        if (budgetBill.getId() != null) {
            operation = "更新";
        }
        String personName = (String) httpSession.getAttribute("personName");
        budgetBill.setApplicant(personName);
        budgetObj = ecBudgeService.save(budgetBill);
        returnObject.setResult(result != null);
        result = (budgetObj != null) ? operation : "失败";
        returnObject.setResultDesc("易耗品采购申请单" + operation + result);
        return returnObject;
    }

}
