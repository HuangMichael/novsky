package com.linkbit.beidou.dao.consumables;

import com.linkbit.beidou.domain.consumables.Consumables;
import com.linkbit.beidou.domain.consumables.DrawList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2016/4/27.
 */
public interface DrawListRepository extends CrudRepository<DrawList, Long> {


    /**
     * 根据id查询领取记录
     */
    DrawList findById(long id);

    /**
     * 保存领取记录
     */
    DrawList save(DrawList drawList);


    /**
     * @param consumables 易耗品对象
     * @return 根据易耗品查询对应的记录
     */
    List<DrawList> findByConsumables(Consumables consumables);


}
