package com.linkbit.beidou.dao.line;


import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.domain.line.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 车站信息查询接口
 */
public interface StationRepository extends CrudRepository<Station, Long> {
    /**
     * 查询所有车站
     */
    List<Station> findAll();

    /**
     * 根据状态查询车站
     */
    List<Station> findByStatus(String status);

    /**
     * 根据id查询
     */
    Station findById(long id);

    /**
     * 根据线路和使用状态查寻车站
     */

    List<Station> findByLineAndStatus(Line line, String status);


}
