package com.linkbit.beidou.controller.app;


import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    /**
     * 初始化展示授权列表
     */
    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        //加载角色列表 显示所有的角色
        List<Role> roleList = roleService.findByStatus("1");
        modelMap.put("roleList", roleList);
        return "/authority/list";
    }
}
