package com.linkbit.beidou.controller.user;


import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.dao.person.PersonRepository;
import com.linkbit.beidou.dao.user.UserRepository;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.user.UserService;
import com.linkbit.beidou.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    VlocationsRepository vlocationsRepository;

    @RequestMapping(value = "/index")
    public String index() {

        return "/user/index";
    }

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        return "/user/list";
    }


    @RequestMapping(value = "/create")
    public String create(ModelMap modelMap) {
        return "/user/create";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject update(@RequestParam("userId") Long userId, @RequestParam("personId") Long personId, @RequestParam("locationId") Long locationId,@RequestParam("status") String status) {
        ReturnObject returnObject = new ReturnObject();
        User user = null;
        if (userId != null && personId != null && locationId != null) {
            user = userService.update(userId, personId, locationId, status);
        }
        if (user != null) {
            returnObject.setResult(true);
            returnObject.setResultDesc("用户更新成功!");
        } else {
            returnObject.setResult(false);
            returnObject.setResultDesc("用户更新失败 !");
        }
        return returnObject;
    }

    /**
     * @return 查询所有的用户信息
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAllUsers();
    }


    /**
     * 保存用户信息
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnObject save(@RequestParam("personId") Long personId, @RequestParam("locationId") Long locationId) {
        User user = new User();
        user.setPerson(personRepository.findById(personId));
        user.setVlocations(vlocationsRepository.findById(locationId));
        user = userService.save(user);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(user != null);
        String resStr = returnObject.getResult() ? "成功" : "失败";
        returnObject.setResultDesc("用户信息保存" + resStr);
        returnObject.getObjectsList().add(user);
        return returnObject;
    }


    /**
     * 保存用户信息
     *
     * @param personId
     * @param locationId
     * @return 创建用户
     */
    @RequestMapping(value = "/createUser", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnObject createUser(@RequestParam("userName") String userName, @RequestParam("personId") Long personId, @RequestParam("locationId") Long locationId) {
        User user = new User();
        user.setUserName(userName);
        user.setPerson(personRepository.findById(personId));
        user.setVlocations(vlocationsRepository.findById(locationId));
        user = userService.createUser(user);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(user != null);
        String resStr = returnObject.getResult() ? "成功" : "失败";
        returnObject.setResultDesc("用户信息创建" + resStr);
        returnObject.getObjectsList().add(user);
        return returnObject;
    }

    /**
     * @param session  当前会话
     * @param modelMap 显示个人信息
     * @return
     */
    @RequestMapping(value = "/profile")
    public String profile(HttpSession session, ModelMap modelMap) {
        User user = SessionUtil.getCurrentUserBySession(session);
        modelMap.put("user", user);
        return "/user/profile";
    }


    /**
     * @param id 用户id
     * @return 根据用户id查询
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }


}
