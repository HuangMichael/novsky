package com.linkbit.beidou.controller.search;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.dao.role.RoleRepository;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.role.RoleService;
import com.linkbit.beidou.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    ResourceService resourceService;


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

        return getPageUtils().searchByService(roleService,searchPhrase, 2, current, rowCount);
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
        List<Role> dataList = roleService.findByConditions(param,2);
        roleService.setDataList(dataList);
        roleService.exportExcel(request, response, docName, titles, colNames);
    }


}





