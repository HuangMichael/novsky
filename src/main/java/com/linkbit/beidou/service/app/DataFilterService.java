package com.linkbit.beidou.service.app;

import com.linkbit.beidou.dao.app.dataFilter.DataFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class DataFilterService {

    @Autowired
    DataFilterRepository dataFilterRepository;


    public DataFilterRepository getDataFilterRepository() {
        return dataFilterRepository;
    }

    public void setDataFilterRepository(DataFilterRepository dataFilterRepository) {
        this.dataFilterRepository = dataFilterRepository;
    }
}
