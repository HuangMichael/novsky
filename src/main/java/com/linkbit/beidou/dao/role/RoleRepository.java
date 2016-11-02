package com.linkbit.beidou.dao.role;


import com.linkbit.beidou.domain.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/1/8 0008.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    /**
     * 查询所有角色
     */
    List<Role> findAll();

    /**
     * 根据状态查询所有角色
     */
    List<Role> findByStatus(String status);

    /**
     * 根据id查询
     */
    Role findById(long id);


    /**
     * @param roleDesc 角色描述
     * @param pageable
     * @return 根据角色描述模糊查询
     */
    Page<Role> findByRoleDescContains(String roleDesc, Pageable pageable);


    /**
     * 保存角色信息;
     */
    Role save(Role role);


}
