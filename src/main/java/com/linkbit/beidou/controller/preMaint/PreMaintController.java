package com.linkbit.beidou.controller.preMaint;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.preMaint.PreMaint;
import com.linkbit.beidou.domain.preMaint.PreMaintWorkOrder;
import com.linkbit.beidou.domain.preMaint.VpreMaint;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String location = this.getUserLocation(session);
        Page<VpreMaint> page = preMaintService.findByPmDescContainingAndLocationStartingWith(searchPhrase, location, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
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
    @RequestMapping(value = "/findAllIds")
    @ResponseBody
    public List<Long> selectAllId() {
        String location = getUserLocation();
        List<Long> idList = preMaintService.selectAllId(location);
        for (Long id : idList) {
            System.out.println("id---------------" + id);
        }
        return idList;
    }


    /**
     * 保存信息
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ReturnObject save(PreMaint preMaint) {
        preMaint.setLocation(getUserLocation());
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
        List<VpreMaint> dataList = preMaintService.findByPmDescContains(param);
        preMaintService.setDataList(dataList);
        preMaintService.exportExcel(request, response, docName, titles, colNames);
    }

}
