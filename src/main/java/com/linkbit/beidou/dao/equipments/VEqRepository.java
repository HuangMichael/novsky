package com.linkbit.beidou.dao.equipments;


import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface VEqRepository extends PagingAndSortingRepository<Vequipments, Long>{

}
