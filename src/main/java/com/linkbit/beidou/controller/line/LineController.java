package com.linkbit.beidou.controller.line;


import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.service.line.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 * 线路控制器类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/line")
public class LineController {

    @Autowired
    LineService lineService;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Line> lineList = lineService.findByStatus("1");
        modelMap.put("lineList", lineList);
        return "/line/list";
    }

    @RequestMapping(value = "/lines", method = {RequestMethod.GET})
    @ResponseBody
    public List<Line> findAllUseFulLines() {
        return lineService.findByStatus("1");
    }


    /**
     * 根据所有线路信息
     */
    @RequestMapping(value = "/findByStatus")
    @ResponseBody
    public List<Line> findByStatus() {
        List<Line> lineList = lineService.findByStatus("1");
        return lineList;
    }

    /**
     * @param id 根据id查询车站信息
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Line findById(@PathVariable("id") long id) {
        Line line = lineService.findById(id);
        return line;
    }
}
