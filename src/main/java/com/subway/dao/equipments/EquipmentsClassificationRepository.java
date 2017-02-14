package com.subway.dao.equipments;


import com.subway.domain.equipments.EquipmentsClassification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备分类信息查询接口
 */
public interface EquipmentsClassificationRepository extends CrudRepository<EquipmentsClassification, Long> {
    /**
     * 查询所有设备类别
     */
    List<EquipmentsClassification> findAll();

    /**
     * 根据id查询
     */
    EquipmentsClassification findById(long id);

    /**
     * 根据根节点id查询子节点
     */
    @Query("select l from  EquipmentsClassification  l   where  l.parent = :parentId order by l.sortNo")
    List<EquipmentsClassification> findNodeByParent(EquipmentsClassification parent);


    /**
     * 查询根节点
     */
    @Query("select l from  EquipmentsClassification  l   where  l.parent is null  order by l.sortNo")
    List<EquipmentsClassification> findNodeByParentId();


    /**
     * 根据i根据设备类型和描述查询
     *
     * @param classType   设备分类类型  2为站区  1为段区
     * @param description 设备分类描述
     * @return
     */
    List<EquipmentsClassification> findByClassTypeAndDescription(String classType, String description);

}
