package com.linkbit.beidou.service.preMaint;

import com.linkbit.beidou.dao.preMaint.PreMaintRepository;
import com.linkbit.beidou.dao.preMaint.VpreMaintRepository;
import com.linkbit.beidou.domain.preMaint.PreMaint;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<PreMaint> findByPmDescContains(String desc, Pageable pageable) {

        return preMaintRepository.findByDescriptionContains(desc, pageable);
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
}
