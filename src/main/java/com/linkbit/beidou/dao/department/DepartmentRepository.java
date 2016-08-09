package com.linkbit.beidou.dao.department;


import com.linkbit.beidou.domain.department.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 部门信息查询接口
 */
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    /**
     * 查询所有部门信息
     */
    List<Department> findAll();


    @Query("select n from Department n where n.status =1 order by n.parent asc ")
    List<Department> findAllBySort();

    /**
     * 根据状态查询设备类别
     */
    List<Department> findByStatus(String status);

    /**
     * 根据id查询
     */
    Department findById(long id);

    /**
     * 查询一级节点集合
     */
    @OrderBy("sortNo asc")
    List<Department> findByParentAndStatus(Department department, String status);


    /**
     * 根据根节点id查询子节点
     */
    @Query("select l from  Department  l   where  l.parent = :parentId order by l.sortNo")
    List<Department> findNodeByParent(Department parent);


    /**
     * 查询根节点
     */
    @Query("select l from  Department  l   where  l.parent is null  order by l.sortNo")
    List<Department> findNodeByParentId();

    /**
     * 查找外围单位
     */
    @Query("select d  from  Department d where d.status =1 and d.depType ='2'")
    List<Department> findOuterDepartments();


    /**
     * 查找内部部门
     */
    @Query("select d  from  Department d where d.status =1 and d.depType ='1'")
    List<Department> findInnerDepartments();

}
