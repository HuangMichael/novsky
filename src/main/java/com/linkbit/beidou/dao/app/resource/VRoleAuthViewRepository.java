package com.linkbit.beidou.dao.app.resource;

import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.role.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/8/16.
 */
public interface VRoleAuthViewRepository extends CrudRepository<VRoleAuthView, Long> {


    /**
     * @param role
     * @return 根据角色查询角色权限预览视图
     */
    List<VRoleAuthView> findByRole(Role role);

    /**
     * @param role
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     */
    List<VRoleAuthView> findByRoleAndResourceLevel(Role role, Long resourceLevel);

/*
    *//**
     * @param role
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     *//*
    List<VRoleAuthView> findByRoleAndResourceLevelAndParentId(Role role, Long resourceLevel, Long parentId);*/


    /**
     * @param roleIds
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     */
    @Query(nativeQuery = true, value = "select  v.id,v.resource_code,v.resource_desc,v.icon_class,v.resource_url from v_role_auth_view v where v.role_id in :roleList and v.resource_Level =:resourceLevel ")
    List<Object> findByRoleListAndResourceLevel(@Param("roleList") List<Long> roleIds, @Param("resourceLevel") Long resourceLevel);


    /**
     * @param roleIds
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     */
    @Query(nativeQuery = true, value = "select  v.id,v.resource_code,v.resource_desc,v.icon_class,v.resource_url from v_role_auth_view v where v.role_id in :roleList and v.resource_Level =:resourceLevel and v.parent_id =:parentId")
    List<Object> findByRoleListAndResourceLevelAndParentId(@Param("roleList") List<Long> roleIds, @Param("resourceLevel") Long resourceLevel, @Param("parentId") Long parentId);
}
