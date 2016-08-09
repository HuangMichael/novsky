package com.linkbit.beidou.service.app;

import com.linkbit.beidou.dao.app.resource.ResourceRepository;
import com.linkbit.beidou.domain.app.resoure.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 查询所有数据资源
     */
    public List<Resource> findAll() {
        return resourceRepository.findAll();
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
}
