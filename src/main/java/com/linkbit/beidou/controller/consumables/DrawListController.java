package com.linkbit.beidou.controller.consumables;


import com.linkbit.beidou.domain.consumables.DrawList;
import com.linkbit.beidou.service.consumables.DrawListService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/drawList")
public class DrawListController {


    Logger logger = org.slf4j.LoggerFactory.getLogger(DrawListController.class);


    @Autowired
    DrawListService drawListService;

    @RequestMapping(value = "/findByCons/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<DrawList> findByCons(@PathVariable("id") Long id) {
        logger.info("查询易耗品领用记录");
        return drawListService.findByConsumables(id);
    }


}
