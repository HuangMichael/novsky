package com.linkbit.beidou.controller.groups;


import com.linkbit.beidou.dao.groups.GroupsRepository;
import com.linkbit.beidou.domain.user.Groups;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    GroupsRepository groupRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Groups> groupsList = groupRepository.findByStatus("1");
        modelMap.put("groupsList", groupsList);
        List<User> usersList = userService.findByStatus("1");

        System.out.println("usersList--------------" + usersList.size());
        modelMap.put("usersList", usersList);
        return "/groups/list";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public Groups save(Groups groups) {
        return groupRepository.save(groups);
    }

/*
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public Groups addUser(@RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "description", required = false) String description) {

        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setDescription(description);
        group.setStatus("1");
        group = groupRepository.save(group);
        return group;
    }*/

    /**
     * 将用户加入到用户组中
     */
    @RequestMapping(value = "/add2Group", method = {RequestMethod.POST})
    @ResponseBody
    public Groups add2Group(@RequestParam("userIdStrArray") String userIdStrArray, @RequestParam("groupId") Long groupId) {
        Groups groups = userService.addUser2Group(userIdStrArray, groupId);
        return groups;
    }
}
