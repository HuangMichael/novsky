package com.linkbit.beidou.controller.statistics;/**
 * Created by Administrator on 2016/11/4.
 */

import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.resoure.VRoleAuthView;
import com.linkbit.beidou.service.unitsStatistics.UnitsStatisticsService;
import com.linkbit.beidou.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 外委单位完工统计
 *
 * @author
 * @create 2016-11-04 14:01
 **/
@Controller
@EnableAutoConfiguration
@RequestMapping("/unitsStatistics")
public class UnitsStatisticsController extends BaseController {

    @Autowired
    UnitsStatisticsService unitsStatisticsService;


    @RequestMapping(value = "/list")
    public String list(HttpSession httpSession, ModelMap modelMap) {

        return "/unitsStatistics/list";
    }


    /**
     * @param year 年份
     * @return 按照年查询有报修数据的月份
     */
    @RequestMapping(value = "/getDataMonthByYear/{year}")
    @ResponseBody
    public List<String> getDataMonthByYear(@PathVariable("year") String year) {
        return unitsStatisticsService.getDataMonthByYear(year);

    }


    /**
     * @param year 年份
     * @return 按照年查询有报修数据的月份
     */
    @RequestMapping(value = "/getDataDistributed/{unitId}/{year}")
    @ResponseBody
    public List<Long> getOrderDistributedDataYearAndUnit(@PathVariable("unitId") Long unitId, @PathVariable("year") String year) {
        return unitsStatisticsService.getDistributedOrderCountByYearAndUnit(year, unitId);

    }


    /**
     * @param year 年份
     * @return 按照年查询有报修数据的月份
     */
    @RequestMapping(value = "/getDataFinished/{unitId}/{year}")
    @ResponseBody
    public List<Long> getOrderFinishedDataYearAndUnit(@PathVariable("unitId") Long unitId, @PathVariable("year") String year) {
        return unitsStatisticsService.getFinishedOrderCountByYearAndUnit(year, unitId);

    }
}
