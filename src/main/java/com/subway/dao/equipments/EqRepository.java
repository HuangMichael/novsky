package com.subway.dao.equipments;


import com.subway.domain.equipments.Equipments;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface EqRepository extends PagingAndSortingRepository<Equipments, Long>{

}
