package com.linkbit.beidou.dao.groups;


import com.linkbit.beidou.domain.user.Groups;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/1/8 0008.
 */
public interface GroupsRepository extends CrudRepository<Groups, Long> {
    /**
     * 查询所有角色
     */
    List<Groups> findAll();

    /**
     * 根据状态查询所有角色
     */
    List<Groups> findByStatus(String status);

    /**
     * 根据id查询
     */
    Groups findById(long id);

    /**
     * 保存
     */
    Groups save(Groups groups);


}
