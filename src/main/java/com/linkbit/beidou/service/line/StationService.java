package com.linkbit.beidou.service.line;

import com.linkbit.beidou.dao.line.StationRepository;
import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.domain.line.Station;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 * 线路业务类
 */
@Service
public class StationService extends BaseService {

    @Autowired
    StationRepository stationRepository;

    /**
     * 根据id查询站点
     */
    public Station findById(Long id) {
        Station station = null;

        if (id != null) {
            station = stationRepository.findById(id);
        }
        return station;
    }


    /**
     * 根据状态查询所有的站
     */
    public List<Station> findByStatus(String status) {
        status = StringUtils.isEmpty(status) ? "1" : status;
        return stationRepository.findByStatus(status);
    }

    /**
     * 根据状态查询所有的站
     */
    public List<Station> findStationsByLine(Line line) {
        List<Station> stationList = null;
        if (line != null) {
            stationList = stationRepository.findByLineAndStatus(line, "1");
        }
        return stationList;
    }



}