package com.linkbit.beidou.dao.app.resource;

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
    public List<VRoleAuthView> findByRole(Role role);
}
