package com.linkbit.beidou.service.preMaint;

import com.linkbit.beidou.dao.preMaint.PreMaintRepository;
import com.linkbit.beidou.dao.preMaint.VpreMaintRepository;
import com.linkbit.beidou.domain.preMaint.PreMaint;
import com.linkbit.beidou.domain.preMaint.VpreMaint;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangbin on 2016年10月9日11:46:27
 * 预防性维修业务类
 */
@Service
public class PreMaintService extends BaseService {

    @Autowired
    VpreMaintRepository vpreMaintRepository;

    @Autowired
    PreMaintRepository preMaintRepository;

    /**
     * @return 查询所有
     */
    public Page<PreMaint> findAll(Pageable pageable) {

        return preMaintRepository.findAll(pageable);
    }

    /**
     * @return 查询所有
     */
    public Page<VpreMaint> findAllv(Pageable pageable) {

        return vpreMaintRepository.findAll(pageable);
    }


    public Page<VpreMaint> findByPmDescContains(String desc, Pageable pageable) {

        return vpreMaintRepository.findByPmDescContaining(desc, pageable);
    }


    /**
     * @param id 根据id查询
     * @return
     */
    public PreMaint findById(Long id) {

        return preMaintRepository.findOne(id);
    }

    /**
     * @return
     */
    public List<Long> selectAllId() {

        return preMaintRepository.selectAllId();
    }

    /**
     * @param preMaint 预防性维修信息
     * @return 保存预防性维修信息
     */
    public PreMaint save(PreMaint preMaint) {
        return preMaintRepository.save(preMaint);
    }


    /**
     * @param id 根据id删除
     * @return
     */
    public boolean delete(Long id) {
        preMaintRepository.delete(id);
        return preMaintRepository.findOne(id) == null;
    }


    /**
     * @param id       根据id删除
     * @param deadLine
     * @return
     */
    @Transactional
    public List<PreMaint> generatePmOrder(Long id, String deadLine) {
        // TODO: 2016/10/20  根据id获取周期和单位

        PreMaint preMaint = preMaintRepository.findOne(id);
        long frequency = 0;
        String unit = "";
        if (null != preMaint) {
            frequency = preMaint.getFrequency();
            unit = preMaint.getUnit();

        }


        //TODO:2016-10-20  根据deadLine计算出周期数 按照每个周期生成工单


        preMaintRepository.delete(id);
        return null;
    }


}
