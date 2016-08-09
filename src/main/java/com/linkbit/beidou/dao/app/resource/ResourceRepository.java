package com.linkbit.beidou.dao.app.resource;


import com.linkbit.beidou.domain.app.resoure.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 数据资源查询接口
 */
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    /**
     * 查询所有数据资源
     */
    List<Resource> findAll();

    /**
     * 根据状态查询数据资源
     */
    List<Resource> findByStatus(String status);


    /**
     * 根据URL查询
     */
    List<Resource> findByResourceUrl(String status);

    /**
     * 根据id查询数据资源
     */
    Resource findById(long id);

    /**
     * 保存数据资源
     */
    Resource save(Resource resource);


    @Override
    void delete(Resource resource);

    @Query("select count(r)>0 from Resource r where r.parent.id =:pid")
    Boolean hasChildren(@Param("pid") long pid);

    /**
     * 根据上级节点查询
     */
    List<Resource> findByParent(Resource resource);


    /**
     * 根据资源级别查询
     */
    List<Resource> findByResourceLevel(Long resourceLevel);


    /**
     * 根据资源级别查询
     */
    List<Resource> findBystaticFlag(boolean staticOrNot);


}
