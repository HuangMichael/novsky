package com.linkbit.beidou.service.line;

import com.linkbit.beidou.dao.line.StationRepository;
import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.domain.line.Station;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 * 线路业务类
 */
@Service
public class StationSearchService extends BaseService implements Searchable {
    @Autowired
    StationRepository stationRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<Station> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return stationRepository.findByStationNoContainsAndDescriptionContains(array[0], array[1]);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<Station> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return stationRepository.findByStationNoContainsAndDescriptionContains(array[0], array[1], pageable);
    }

}