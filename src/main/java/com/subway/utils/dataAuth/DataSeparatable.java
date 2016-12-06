package com.subway.utils.dataAuth;

/**
 * Created by huangbin on 2016/12/6.
 * 数据隔离接口
 */
public interface DataSeparatable {

    String condition = "location";


    /**
     * 添加条件到业务类
     */
    void addConditionToService();
}
