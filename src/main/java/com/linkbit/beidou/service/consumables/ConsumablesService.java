package com.linkbit.beidou.service.consumables;

import com.linkbit.beidou.dao.consumables.ConsumablesRepository;
import com.linkbit.beidou.domain.consumables.Consumables;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class ConsumablesService extends BaseService{

    @Autowired
    ConsumablesRepository consumablesRepository;


    /**
     * @return 查询所有的易耗品信息
     */
    public List<Consumables> findAll() {

        return consumablesRepository.findAll();
    }


    /**
     * @param status
     * @return 根据状态查询易耗品信息
     */
    public List<Consumables> findBystatus(String status) {

        return consumablesRepository.findByStatus(status);
    }

    /**
     * @param id
     * @return 根据id查询易耗品信息
     */
    public Consumables findById(Long id ) {

        return consumablesRepository.findById(id);
    }


    /**
     * @param consumables 易耗品对象
     * @return 保存易耗品
     */
    public Consumables save(Consumables consumables) {
        return consumablesRepository.save(consumables);
    }



    /**
     * @param type
     * @return 根据状态查询易耗品信息
     */
    public List<Consumables> findByType(String type) {

        return consumablesRepository.findByType(type);
    }

}
