package com.linkbit.beidou.dao.equipments;


import com.linkbit.beidou.domain.equipments.Vequipments;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 设备信息查询接口
 */
public interface VEqRepository extends PagingAndSortingRepository<Vequipments, Long> , JpaSpecificationExecutor<Vequipments> {



    @Query(nativeQuery = true, value = " select * from v_equipments where 1=1  and eq_code like :eqCode"   )
    List<Vequipments> search(@Param("eqCode") String eqCode);
}
