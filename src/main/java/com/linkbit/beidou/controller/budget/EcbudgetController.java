package com.linkbit.beidou.controller.budget;


import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.budget.BudgetBill;
import com.linkbit.beidou.service.budge.BudgeService;
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
    BudgeService budgeService;


    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public List<BudgetBill> data() {
        return budgeService.findAll();
    }

    /**
     * 显示所有的报修车列表信息
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(@RequestParam(value = "current",defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount) {
        Page<BudgetBill> page  = budgeService.findAll(new PageRequest(current-1,rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(budgeService.findAll().size());
        return myPage;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpSession httpSession, ModelMap modelMap) {
        return "/ecbudget/list";
    }
}
