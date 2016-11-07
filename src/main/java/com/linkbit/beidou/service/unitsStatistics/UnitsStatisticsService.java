package com.linkbit.beidou.service.unitsStatistics;

import com.linkbit.beidou.dao.workOrder.VWorkOrderUnitsEfficiencyRankRepository;
import com.linkbit.beidou.dao.workOrder.VWorkOrderUnitsFixedRankRepository;
import com.linkbit.beidou.dao.workOrder.WorkOrderReportCartRepository;
import com.linkbit.beidou.domain.workOrder.VWorkOrderUnitsEfficiencyRank;
import com.linkbit.beidou.domain.workOrder.VWorkOrderUnitsFixedRank;
import com.linkbit.beidou.object.statistics.StatisticsDistributedObject;
import com.linkbit.beidou.object.statistics.StatisticsFinishedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/11/4.
 * 外委单位统计业务类
 */
@Service
public class UnitsStatisticsService {

    @Autowired
    WorkOrderReportCartRepository workOrderReportCartRepository;


    @Autowired
    VWorkOrderUnitsFixedRankRepository workOrderUnitsFixedRankRepository;

    @Autowired
    VWorkOrderUnitsEfficiencyRankRepository vWorkOrderUnitsEfficiencyRankRepository;


    //按照年查询当年有数据的月份


    /**
     * @param year 按照年查询有报修数据的月份
     * @return yyyy
     */
    public List<String> getDataMonthByYear(String year) {
        return workOrderReportCartRepository.getDataMonthByYear(year);
    }


    /**
     * @param year   年份
     * @param unitId 外委单位ID
     * @return 按照年度和外委单位查询分配工单的数量
     */
    public List<StatisticsDistributedObject> getDistributedOrderCountByYearAndUnit(String year, Long unitId) {

        return workOrderReportCartRepository.getDistributedOrderCountByYearAndUnit(year, unitId);
    }


    /**
     * @param year   年份
     * @param unitId 外委单位ID
     * @return 按照年度和外委单位查询完工工单的数量
     */
    public List<StatisticsFinishedObject> getFinishedOrderCountByYearAndUnit(String year, Long unitId) {

        return workOrderReportCartRepository.getFinishedOrderCountByYearAndUnit(year, unitId);
    }


    /**
     * @param reportYear 报修年份
     * @return 按照报修年份查询
     */
    public List<VWorkOrderUnitsFixedRank> findByReportYear(String reportYear) {

        return workOrderUnitsFixedRankRepository.findByReportYear(reportYear);
    }


    /**
     * @param reportYear
     * @return 按照报修年份查询时效排名
     */
    public List<VWorkOrderUnitsEfficiencyRank> findEffRankByReportYear(String reportYear) {

        return vWorkOrderUnitsEfficiencyRankRepository.findByReportYear(reportYear);
    }

}
