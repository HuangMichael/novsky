package com.linkbit.beidou.domain.workOrder;

import java.util.List;

/**
 *  接受list参数
 */
public class WorkOrderReportForm {
    List<WorkOrderReportDetail> orderReportList;

    public List<WorkOrderReportDetail> getOrderReportList() {
        return orderReportList;
    }

    public void setOrderReportList(List<WorkOrderReportDetail> orderReportList) {
        this.orderReportList = orderReportList;
    }
}
