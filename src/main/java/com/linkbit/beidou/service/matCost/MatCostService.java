package com.linkbit.beidou.service.matCost;

import com.linkbit.beidou.dao.app.resource.ResourceRepository;
import com.linkbit.beidou.dao.app.resource.VRoleAuthViewRepository;
import com.linkbit.beidou.dao.macCost.MatCostRepository;
import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.role.Role;
import com.linkbit.beidou.domain.user.User;
import com.linkbit.beidou.service.role.RoleService;
import com.linkbit.beidou.service.user.UserService;
import com.linkbit.beidou.utils.SessionUtil;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin
 * 2016年9月29日10:03:31
 * <p/>
 * 物料消耗查询业务类
 */
@Service
public class MatCostService {

    @Autowired
    public ResourceRepository resourceRepository;

    @Autowired
    public MatCostRepository matCostRepository;


    /**
     * 查询所有数据资源
     */
    public Page<MatCost> findAll(Pageable pageable) {
        return matCostRepository.findAll(pageable);
    }


   /* *//**
     * @param location 位置
     * @param ecName   物资易耗品名称
     * @param pageable 可分页
     * @return
     *//*
    public Page<MatCost> findByCondition(String location, String ecName, Pageable pageable) {

        return null;

    }*/
}
