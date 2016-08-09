package com.linkbit.beidou.service.app;

import com.linkbit.beidou.dao.groups.GroupsRepository;
import com.linkbit.beidou.domain.user.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 * <p/>
 * 数据用户组业务类
 */
@Service
public class GroupsService {

    @Autowired
    public GroupsRepository groupsRepository;


    /**
     * 查询所有数据用户组
     */
    public List<Groups> findAll() {
        return groupsRepository.findAll();
    }

    /**
     * 根据状态查询数据用户组
     */
    public List<Groups> findByStatus(String status) {

        return groupsRepository.findByStatus(status);

    }

    /**
     * 根据id查询数据用户组
     */
    public Groups findById(long id) {

        return groupsRepository.findById(id);

    }

    /**
     * 保存数据用户组
     */
    public Groups save(Groups resource) {

        return groupsRepository.save(resource);
    }

    /**
     * 保存数据用户组
     */
    public void delete(Groups resource) {

        groupsRepository.delete(resource);
    }


    /**
     * 根据id删除数据用户组
     */
    public void delete(Long pid) {
        groupsRepository.delete(pid);
    }


}
