package com.linkbit.beidou.service.user;

import com.linkbit.beidou.dao.user.UserRepository;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.object.PageObject;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangbin  on 2016/4/14.
 */

@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;
    /**
     * 根据状态查询用户
     */
    public List<User> findByStatus(String status) {
        return userRepository.findByStatus(status);
    }


    /**
     * 根据状态查询用户
     */
    public PageObject getPageObject(String status) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 240; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setUserName("GRQETQE" + (i + 1));
            user.setStatus("1");
            userList.add(user);
        }
        PageObject pageObject = new PageObject();
        pageObject.setCurrent(1l);
        pageObject.setRowCount(6l);
        pageObject.setRows(userList);
        pageObject.setTotal(userList.size() + 0l);
        return pageObject;
    }


    /**
     * @return 查询所有的用户
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 对用户进行加密
     */
    public User save(User user) {
        String password = user.getPassword();
        if (user.getPassword() != null) {
            user.setPassword(MD5Util.md5(password));
        }
        user.setStatus("1");
        return userRepository.save(user);
    }

    /**
     * 查询加密后的用户
     */

    public List<User> findByUserNameAndPasswordAndStatus(String userName, String password, String status) {

        return userRepository.findByUserNameAndPasswordAndStatus(userName, password, status);
    }
}
