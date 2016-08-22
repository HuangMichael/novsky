package com.linkbit.beidou.dao.user;


import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/1/8 0008.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 查询所有菜单
     */
    List<User> findAll();

    /**
     * 根据状态查询所有菜单
     */
    List<User> findByStatus(String status);

    /**
     * 根据id查询
     */
    User findById(long id);

    /**
     * 根据用户名查询用户
     */
    List<User> findByUserName(String userName);

    /**
     * 根据用户名和密码查询用户
     */
    List<User> findByUserNameAndPasswordAndStatus(String userName, String password, String status);


    User save(User user);

    /**
     * @param userName 用户名
     * @param status   用户状态
     * @return
     */
    List<User> findByUserNameAndStatus(String userName, String status);


    /**
     * 查询不在当前角色中的用户信息
     */
    @Query(nativeQuery = true,value="SELECT  u.id,u.user_name FROM t_user u WHERE u.id NOT IN (SELECT ur.user_id FROM  t_role_user ur  WHERE ur.role_id = :roleId) AND u.status = 1")
    List<Object> findUsersNotInRole(@Param("roleId") Long roleId);

}
