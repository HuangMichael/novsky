package com.linkbit.beidou.dao.line;


import com.linkbit.beidou.domain.line.Line;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 线路信息查询接口
 */
public interface LineRepository extends CrudRepository<Line, Long> {
    /**
     * 查询所有线路
     */
    List<Line> findAll();

    /**
     * 根据状态查询线路
     */
    List<Line> findByStatus(String status);

    /**
     * 根据id查询
     */
    Line findById(long id);







}
