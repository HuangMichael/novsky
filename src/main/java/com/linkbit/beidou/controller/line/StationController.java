package com.linkbit.beidou.controller.line;


import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.domain.line.Station;
import com.linkbit.beidou.service.line.LineService;
import com.linkbit.beidou.service.line.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 * 设备台账控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/station")
public class StationController {


    @Autowired
    StationService stationService;
    @Autowired
    LineService lineService;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Station> stationList = stationService.findByStatus("1");
        modelMap.put("stationList", stationList);
        return "/station/list";
    }

    /**
     * 根据线路查询站点
     */
    @RequestMapping(value = "/findStationByLine/{lineId}")
    @ResponseBody
    public List<Station> findStationByLine(@PathVariable("lineId") Long lineId) {
        Line line = lineService.findById(lineId);
        if (null != line) {
            return stationService.findStationsByLine(line);
        } else {
            return null;
        }
    }


    /**
     * 根据所有车站信息
     */
    @RequestMapping(value = "/findByStatus")
    @ResponseBody
    public List<Station> findByStatus() {
        List<Station> stationList = stationService.findByStatus("1");
        return stationList;
    }
    /**
     * @param id 根据id查询车站信息
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Station findById(@PathVariable("id") long id) {
        Station station = stationService.findById(id);
        return station;
    }
}
