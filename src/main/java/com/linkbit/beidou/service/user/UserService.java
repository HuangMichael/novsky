package com.linkbit.beidou.service.user;

import com.linkbit.beidou.dao.groups.GroupsRepository;
import com.linkbit.beidou.dao.user.UserRepository;
import com.linkbit.beidou.domain.user.Groups;
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

    @Autowired
    GroupsRepository groupsRepository;

    /**
     * 将用户加入到用户组中
     */
    public Groups addUser2Group(String userIdStrArrary, Long groupId) {
        if (StringUtils.isEmpty(userIdStrArrary)) {
            return null;
        }
        if (null == groupId || groupId == 0) {
            return null;
        }
        User user;
        Set<User> userSet = new HashSet<User>();
        Groups groups = groupsRepository.findById(groupId);
        String[] userIdsArray = userIdStrArrary.split(",");
        for (String userId : userIdsArray) {
            user = userRepository.findById(Long.valueOf(userId));
            userSet.add(user);
        }
        groups.setUsers(userSet);
        groups = groupsRepository.save(groups);
        return groups;
    }

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
        pageObject.setTotal(userList.size()+0l);
        return pageObject;
    }


    /*{
        "current": 1,
            "rowCount": 10,
            "rows": [
        {
            "id": 19,
                "sender": "123@test.de",
                "received": "2014-05-30T22:15:00"
        },
        {
            "id": 14,
                "sender": "123@test.de",
                "received": "2014-05-30T20:15:00"
        }
        ]
        "total": 1123
    }*/


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
