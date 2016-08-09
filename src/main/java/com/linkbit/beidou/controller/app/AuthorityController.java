package com.linkbit.beidou.controller.app;


import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.user.Groups;
import com.linkbit.beidou.service.app.GroupsService;
import com.linkbit.beidou.service.app.ResourceService;
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
    GroupsService groupsService;

    /**
     * 初始化展示授权列表
     */
    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        // 初始化加载用户组
        List<Groups> groupsList = groupsService.findByStatus("1");
        //初始化加载资源树
        List<Resource> resourceList = resourceService.findByStatus("1");
        modelMap.put("resourceList", resourceList);
        modelMap.put("groupsList", groupsList);
        return "/authority/list";
    }
}
