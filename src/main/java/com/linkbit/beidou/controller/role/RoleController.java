package com.linkbit.beidou.controller.role;


import com.linkbit.beidou.dao.role.RoleRepository;
import com.linkbit.beidou.domain.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/index")
    public String index() {

        return "/role/index";
    }


   /* @RequestMapping(value = "/list")
    public ModelAndView list(ModelMap modelMap) {
        List<User> userList = userRepository.findAll();
        modelMap.put("userList", userList);
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("userList");
        return modelAndView;
    }*/


    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Role> roleList = roleRepository.findAll();
        modelMap.put("roleList", roleList);
        return "/role/list";
    }


    /**
     * 保存角色信息
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public Role save(@RequestParam("roleName") String roleName, @RequestParam("roleDesc") String roleDesc, @RequestParam("sortNo") Long sortNo, @RequestParam("status") String status) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setSortNo(sortNo);
        role.setStatus(status);
        role = roleRepository.save(role);
        return role;
    }


    /**
     * 载入明细信息
     */
    @RequestMapping(value = "/detail/{roleId}")
    public String detail(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        Role role = roleRepository.findById(roleId);
        modelMap.put("role", role);
        return "/role/detail";
    }


}
