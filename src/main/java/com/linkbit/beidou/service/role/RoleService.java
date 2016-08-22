package com.linkbit.beidou.service.role;

import com.linkbit.beidou.dao.role.RoleRepository;
import com.linkbit.beidou.dao.user.UserRepository;
import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.CommonStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色业务类
 */
@Service
public class RoleService extends BaseService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * 根据状态查询用户
     */
    public List<Role> findByStatus(String status) {
        return roleRepository.findByStatus(status);
    }


    /**
     * 根据ID查询角色
     */
    public Role findById(Long id) {
        return roleRepository.findById(id);
    }


    /**
     * @param role
     * @return 保存角色信息
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }


    /**
     * @return 查询在用角色
     */
    public List<Role> findActiveRole() {
        return roleRepository.findByStatus(CommonStatusType.STATUS_YES);
    }


    /**
     * 查询出不在当前角色中的人员
     */

    public List<Object> findUsersNotInRole(Long roleId) {
        return userRepository.findUsersNotInRole(roleId);
    }
}
