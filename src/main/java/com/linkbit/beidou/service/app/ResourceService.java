package com.linkbit.beidou.service.app;

import com.linkbit.beidou.dao.app.resource.ResourceRepository;
import com.linkbit.beidou.dao.app.resource.VRoleAuthViewRepository;
import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.service.role.RoleService;
import com.linkbit.beidou.service.user.UserService;
import com.linkbit.beidou.utils.SessionUtil;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 * <p/>
 * 数据资源业务类
 */
@Service
public class ResourceService {

    @Autowired
    public ResourceRepository resourceRepository;

    @Autowired
    VRoleAuthViewRepository vRoleAuthViewRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    /**
     * 查询所有数据资源
     */
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }


    /**
     * 查询所有数据资源
     */
    public List<Resource> findApps() {
        return resourceRepository.findByResourceLevel(1l);
    }

    /**
     * 根据状态查询数据资源
     */
    public List<Resource> findByStatus(String status) {

        return resourceRepository.findByStatus(status);

    }

    /**
     * 根据id查询数据资源
     */
    public Resource findById(long id) {

        return resourceRepository.findById(id);

    }

    /**
     * 保存数据资源
     */
    public Resource save(Resource resource) {

        return resourceRepository.save(resource);
    }

    /**
     * 保存数据资源
     */
    public void delete(Resource resource) {

        resourceRepository.delete(resource);
    }


    /**
     * 根据id删除数据资源
     */
    public void delete(Long pid) {
        resourceRepository.delete(pid);
    }

    /**
     * 检查是否有子节点
     */
    public boolean hasChildren(Long pid) {

        return resourceRepository.hasChildren(pid);
    }


    /**
     * 根据状态查询数据资源
     */
    public List<Resource> findByParent(Resource parent) {

        return resourceRepository.findByParent(parent);

    }

    /**
     * 根据状态查询数据资源
     */
    public List<Resource> findByResourceLevel(Long resourceLevel) {

        return resourceRepository.findByResourceLevel(resourceLevel);

    }


    /**
     * 根据状态查询数据资源
     */
    public List<Resource> findByResourceLevelLessThan(Long resourceLevel) {

        return resourceRepository.findByResourceLevelLessThan(resourceLevel);

    }

    /**
     * 根据状态查询静态数据资源
     */
    public List<Resource> findAllStaticResources() {

        return resourceRepository.findBystaticFlag(true);
    }

    /**
     * 检查资源合法性
     */

    public Boolean checkResource(List<Resource> resourceList, String url) {

        boolean result = false;
        for (Resource resource : resourceList) {
            if (url.contains(resource.getResourceUrl())) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * @param types  文件类型数组
     * @param suffix 文件后缀名
     * @return
     */
    public boolean containsType(String types[], String suffix) {

        boolean result = false;
        for (String type : types) {
            if (type.equals(suffix)) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * 保存数据资源
     */
    public Resource addResource(String url) {
        Resource resource = new Resource();
        boolean exits = resourceRepository.findByResourceUrl(url).size() > 0;
        if (!exits) {
            resource.setDescription(url);
            resource.setResourceUrl(url);
            resource.setResourceName(url);
            resource.setStaticFlag(true);
            resource = resourceRepository.save(resource);
        }
        return resource;
    }


    /**
     * @param idStr
     * @return 根据ID列表查询对应的资源集合
     */
    public List<Resource> findResourceListInIdStr(String idStr) {
        List<Long> idList = StringUtils.str2List(idStr, ",");
        List<Resource> resourceList = resourceRepository.findResourceIdInIdList(idList);
        return resourceList;
    }

/*
    *//**
     * @param roleId
     * @param resourceLevel
     * @return 根据角色和资源级别查询资源
     *//*
    public List<VRoleAuthView> findAppsByRoleId(Long roleId, Long resourceLevel) {
        Role role = roleService.findById(roleId);
        return vRoleAuthViewRepository.findByRoleAndResourceLevel(role, resourceLevel);
    }


    *//**
     * @param roleId
     * @param resourceLevel
     * @return 根据角色和资源级别查询资源
     *//*
    public List<VRoleAuthView> findAppsByRoleIdAndParentId(Long roleId, Long resourceLevel, Long parentId) {
        Role role = roleService.findById(roleId);
        return vRoleAuthViewRepository.findByRoleAndResourceLevelAndParentId(role, resourceLevel, parentId);
    }*/


    /**
     * @param userName 用户名
     * @return 根据userId和资源级别查询资源
     */
    public List<VRoleAuthView> findResourcesByUserNameAndResourceLevel(String userName, Long resourceLevel) {
        //首先根据用户id查询出用户对应的角色
        List<VRoleAuthView> vRoleAuthViewList = null;
        User user = userService.findByUserNameAndStatus(userName, "1");
        if (user != null) {
            List<Role> roleList = user.getRoleList();
            vRoleAuthViewList = vRoleAuthViewRepository.findByRoleListAndResourceLevel(roleList, resourceLevel);
        }
        return vRoleAuthViewList;
    }

    /**
     * @param userName 用户名
     * @return 根据userId和资源级别查询资源
     */
    public List<VRoleAuthView> findResourcesByUserNameAndResourceLevelAndParentId(String userName, Long resourceLevel, Long parentId) {
        //首先根据用户id查询出用户对应的角色
        List<VRoleAuthView> vRoleAuthViewList = null;
        User user = userService.findByUserNameAndStatus(userName, "1");
        if (user != null) {
            List<Role> roleList = user.getRoleList();
            vRoleAuthViewList = vRoleAuthViewRepository.findByRoleListAndResourceLevelAndParentId(roleList, resourceLevel, parentId);
        }
        return vRoleAuthViewList;
    }


    /**
     * 根据AppName查询应用菜单
     */
    public List<VRoleAuthView> findAppMenusByController(HttpSession httpSession, String controllerName) {
        User user = SessionUtil.getCurrentUserBySession(httpSession);
        List<Role> roleList = user.getRoleList();
        String appName[] = controllerName.split("Controller");
        System.out.println("应用名称------------------------" + appName[0].toUpperCase());
        return vRoleAuthViewRepository.findByRoleListAppName(roleList, appName[0].toUpperCase());
    }

}
