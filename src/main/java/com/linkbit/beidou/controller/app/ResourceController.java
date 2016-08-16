package com.linkbit.beidou.controller.app;


import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.service.app.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Resource> resourceList = resourceService.findByResourceLevelLessThan(2l);
        Resource resource = resourceList.get(0);
        modelMap.put("resourceList", resourceList);
        modelMap.put("resource", resource);
        return "/resource/list";


    }

    /**
     * 查询所有的节点
     */

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> findAll() {
        List<Resource> resourceList = resourceService.findAll(); //查询二级模块
        return resourceList;
    }

    /**
     * 查询所有的节点
     */

    @RequestMapping(value = "/findApps", method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> findApps() {
        List<Resource> resourceList = resourceService.findAll(); //查询二级模块
        return resourceList;
    }

    /**
     * 查询一级模块
     */

    @RequestMapping(value = "/findResources/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<VRoleAuthView> findAppsByRoleId(@PathVariable("roleId") Long roleId) {
        List<VRoleAuthView> vRoleAuthViews = resourceService.findAppsByRoleId(roleId, 1l);
        return vRoleAuthViews;
    }


    /**
     * 查询二级应用
     *
     * @param roleId   角色ID
     * @param parentId 上级Id
     * @return 查询模块下的应用菜单
     */

    @RequestMapping(value = "/findResources/{roleId}/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<VRoleAuthView> findAppsByRoleIdAndParentId(@PathVariable("roleId") Long roleId, @PathVariable("parentId") Long parentId) {
        List<VRoleAuthView> vRoleAuthViews = resourceService.findAppsByRoleIdAndParentId(roleId, 2l, parentId);
        return vRoleAuthViews;
    }


    /**
     * 查询根节点
     */
    @RequestMapping(value = "/create/{id}")
    public String create(@PathVariable("id") Long id, ModelMap modelMap) {
        Resource parent = resourceService.findById(id);
        Resource newObj = new Resource();
        newObj.setParent(parent);
        //加载上级列表
        List<Resource> resourceList = resourceService.findAll();
        modelMap.put("resourceList", resourceList);
        modelMap.put("resource", newObj);
        modelMap.put("parent", parent);
        //查询出所有的设备分类
        return "/resource/create";
    }


    /**
     * 查询根节点
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        String url = "/resource";
        if (id != null) {
            url += "/detail";
        } else {
            url += "/list/";
        }
        Resource resource = resourceService.findById(id);
        List<Resource> resourceList = new ArrayList<Resource>();
        if (resource != null) {
            resourceList = resourceService.findByParent(resource);
        }
        modelMap.put("resource", resource);
        modelMap.put("resourceList", resourceList);
        return url;
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Resource findById(@PathVariable("id") Long id) {
        Resource resource = resourceService.findById(id);
        return resource;
    }

    /**
     * 保存资源信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Resource save(@RequestParam("resourceName") String resourceName,
                         @RequestParam("description") String description,
                         @RequestParam("resourceUrl") String resourceUrl,
                         @RequestParam(value = "resourceLevel", required = false) Long resourceLevel,
                         @RequestParam(value = "lid", required = false) Long lid,
                         @RequestParam(value = "pid", required = false) Long pid,
                         @RequestParam("status") String status,
                         @RequestParam(value = "sortNo", required = false) Long sortNo

    ) {

        Resource parent = resourceService.findById(pid);
        Resource resource;
        if (lid != null && lid != 0) {
            resource = resourceService.findById(lid);
        } else {
            resource = new Resource();
            resource.setParent(parent);
        }
        resource.setResourceName(resourceName);
        resource.setDescription(description);
        resource.setResourceUrl(resourceUrl);
        resource.setResourceLevel(resourceLevel);
        resource.setSortNo(sortNo);
        resource.setStatus(status);
        resource = resourceService.save(resource);
        return resource;
    }


    /**
     * 删除位置信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        boolean hasChildren = resourceService.hasChildren(id);
        if (!hasChildren) {
            resourceService.delete(id);
            return true;
        } else {
            return false;
        }
    }

    //加载一级模块
}
