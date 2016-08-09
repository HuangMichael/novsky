package com.linkbit.beidou.service.consumables;

import com.linkbit.beidou.dao.consumables.ConsumablesRepository;
import com.linkbit.beidou.dao.consumables.DrawListRepository;
import com.linkbit.beidou.domain.consumables.DrawList;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class DrawListService extends BaseService {

    @Autowired
    DrawListRepository drawListRepository;

    @Autowired

    ConsumablesRepository consumablesRepository;

    /**
     * @param drawList 领取记录对象
     * @return 保存领取记录
     */
    public DrawList save(DrawList drawList) {
        return drawListRepository.save(drawList);
    }


    /**
     * @param id
     * @return 根据状态查询领取记录信息
     */
    public DrawList findById(Long id) {

        return drawListRepository.findById(id);
    }


    /**
     * @param id
     * @return 根据状态查询领取记录信息
     */
    public List<DrawList> findByConsumables(Long id) {

        return drawListRepository.findByConsumables(consumablesRepository.findById(id));
    }
}
