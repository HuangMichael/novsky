package com.linkbit.beidou.dao.app.resource;

import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.role.Role;
import org.springframework.data.repository.CrudRepository;

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


    /**
     * @param role
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     */
    List<VRoleAuthView> findByRoleAndResourceLevelAndParentId(Role role, Long resourceLevel, Long parentId);


    /**
     * @param role
     * @param resourceLevel
     * @return 根据角色和资源级别查询
     */
    /*List<VRoleAuthView> findByRoleAndResourceLevelAndParent(Role role, Long resourceLevel,Long parent);*/
}
