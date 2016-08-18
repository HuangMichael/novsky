package com.linkbit.beidou.controller.user;


import com.linkbit.beidou.dao.user.UserRepository;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.user.UserService;
import com.linkbit.beidou.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/index")
    public String index() {

        return "/user/index";
    }

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
/*        List<User> userList = userService.findAllUsers();
        modelMap.put("userList", userList);*/
        return "/user/list";
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
    public ReturnObject save(User user) {
        user.setPassword("123456");
        user = userService.save(user);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(user != null);
        String resStr = returnObject.getResult() ? "成功" : "失败";
        returnObject.setResultDesc("用户信息保存" + resStr);
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
