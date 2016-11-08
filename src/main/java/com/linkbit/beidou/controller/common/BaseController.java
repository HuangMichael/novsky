package com.linkbit.beidou.controller.common;


import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.budget.VbudgetBill;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.utils.StringUtils;
import com.linkbit.beidou.utils.export.docType.ExcelDoc;
import com.linkbit.beidou.utils.export.exporter.DataExport;
import com.linkbit.beidou.utils.export.exporter.ExcelDataExporter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
 * 基础控制器
 */
@Controller
@Data
@EnableAutoConfiguration
public abstract class BaseController {

    @Autowired
    ResourceService resourceService;


    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {
        //加载查询菜单
        String controllerName = this.getClass().getSimpleName().split("Controller")[0];
        List<VRoleAuthView> appMenus = resourceService.findAppMenusByController(httpSession, controllerName.toUpperCase());
        modelMap.put("appMenus", appMenus);
        String url = "/" + StringUtils.lowerCaseCamel(controllerName) + "/list";
        System.out.println("url----------" + url);
        return url;
    }
}

