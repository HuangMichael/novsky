package com.linkbit.beidou.dao.workOrder;

import com.linkbit.beidou.domain.workOrder.ConsumptiveMaterial;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by huangbin on 2016/4/29.
 */
public interface ConsumptiveMaterialRepository extends CrudRepository<ConsumptiveMaterial, Long> {

    /**
     * @param consumptiveMaterial 保存耗材信息
     * @return
     */
    ConsumptiveMaterial save(ConsumptiveMaterial consumptiveMaterial);
}
