package com.linkbit.beidou.service.equipments;

import com.linkbit.beidou.dao.equipments.VEqRepository;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/5/4.
 * 设备查询类
 */
@Service
public class EquipmentSearchService extends BaseService implements Searchable {

    @Autowired
    VEqRepository vEqRepository;

    public Page<Vequipments> findByConditions(String searchPhrase, int paramsSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramsSize);
        return vEqRepository.findByEqCodeContainsAndEqNameContainsAndLocNameContainsAndEqClassContains(array[0], array[1], array[2], array[3], pageable);
    }


    /**
     * @param searchPhrase
     * @return 根据角色描述关键字进行查询
     */

    public List<Vequipments> findByConditions(String searchPhrase, int paramsSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramsSize);
        return vEqRepository.findByEqCodeContainsAndEqNameContainsAndLocNameContainsAndEqClassContains(array[0], array[1], array[2], array[3]);
    }
}
