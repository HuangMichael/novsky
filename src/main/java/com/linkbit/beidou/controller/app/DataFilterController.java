package com.linkbit.beidou.controller.app;


import com.linkbit.beidou.domain.app.dataFilter.DataFilter;
import com.linkbit.beidou.service.app.DataFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/dataFilter")
public class DataFilterController {

    @Autowired
    DataFilterService dataFilterService;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<DataFilter> dataFilterList = dataFilterService.getDataFilterRepository().findByStatus("1");
        modelMap.put("dataFilterList", dataFilterList);
        return "/dataFilter/list";


    }


}
