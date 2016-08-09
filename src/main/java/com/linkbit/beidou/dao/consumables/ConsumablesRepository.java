package com.linkbit.beidou.dao.consumables;

import com.linkbit.beidou.domain.consumables.Consumables;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/4/27.
 */
public interface ConsumablesRepository extends CrudRepository<Consumables, Long> {


    /**
     * 查询所有易耗品信息
     */
    @Query("select  c from Consumables c order by  c. id desc")
    List<Consumables> findAll();


    /**
     * 根据状态查询易耗品
     */
    @Query("select  c from Consumables c where c.status=:status order by  c. id desc")
    List<Consumables> findByStatus(@Param("status") String status);

    /**
     * 根据id查询易耗品
     */
    Consumables findById(long id);

    /**
     * 保存易耗品
     */
    Consumables save(Consumables consumables);


    /**
     * 根据状态查询易耗品
     */
    @Query("select  c from Consumables c where c.type=:type order by  c. id desc")
    List<Consumables> findByType(@Param("type") String type);


}
