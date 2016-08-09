package com.linkbit.beidou.dao.app.dataFilter;


import com.linkbit.beidou.domain.app.dataFilter.DataFilterCondition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 数据过滤器条件查询接口
 */
public interface DataFilterConditionRepository extends CrudRepository<DataFilterCondition, Long> {
    /**
     * 查询所有数据过滤器条件
     */
    List<DataFilterCondition> findAll();

    /**
     * 根据状态查询数据过滤器条件
     */
    List<DataFilterCondition> findByStatus(String status);

    /**
     * 根据id查询数据过滤器条件
     */
    DataFilterCondition findById(long id);

}
