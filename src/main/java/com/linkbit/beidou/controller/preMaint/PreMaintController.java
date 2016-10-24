package com.linkbit.beidou.controller.preMaint;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.preMaint.PreMaint;
import com.linkbit.beidou.domain.preMaint.PreMaintWorkOrder;
import com.linkbit.beidou.domain.preMaint.VpreMaint;
import com.linkbit.beidou.domain.workOrder.WorkOrderReportCart;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.service.preMaint.PreMaintService;
import com.linkbit.beidou.utils.SessionUtil;
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
 * Created by huangbin on 2015/12/23 0023.
 * 预防性维修控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/preMaint")
public class PreMaintController extends BaseController {
    @Autowired
    PreMaintService preMaintService;
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
    public MyPage data(HttpSession session, @RequestParam(value = "current", defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Page<VpreMaint>
                page = preMaintService.findByPmDescContains(searchPhrase, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }


    /**
     * @param httpSession
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {
        String controllerName = this.getClass().getSimpleName().split("Controller")[0];
        List<VRoleAuthView> appMenus = resourceService.findAppMenusByController(httpSession, controllerName.toUpperCase());
        modelMap.put("appMenus", appMenus);
        return "/preMaint/list";
    }


    /**
     * 根据id查询
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public PreMaint findById(@PathVariable("id") Long id) {
        return preMaintService.findById(id);
    }


    /**
     * 根据id查询
     */
    @RequestMapping(value = "/selectAllId")
    @ResponseBody
    public List<Long> selectAllId() {
        return preMaintService.selectAllId();
    }


    /**
     * 保存信息
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ReturnObject save(PreMaint preMaint) {
        preMaint = preMaintService.save(preMaint);
        return commonDataService.getReturnType(preMaint != null, "预防性维修信息保存成功", "预防性维修信息保存失败");
    }


    /**
     * 保存信息
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        Boolean result = preMaintService.delete(id);
        return commonDataService.getReturnType(result, "预防性维修信息保存成功", "预防性维修信息保存失败");
    }


    /**
     * 生成预防性维修工单
     *
     * @param pmId
     * @param deadLine
     * @return
     */
    @RequestMapping(value = "/genPmOrder")
    @ResponseBody
    public ReturnObject generatePmOrder(@RequestParam("pmId") Long pmId, @RequestParam("deadLine") String deadLine) {
        List<PreMaintWorkOrder> preMaintList = preMaintService.generatePmOrder(pmId, deadLine);
        return commonDataService.getReturnType(preMaintList != null, "预防性维修信息生成成功", "预防性维修信息生成失败");
    }
}
