package com.linkbit.beidou.service.preMaint;

import com.linkbit.beidou.dao.preMaint.VpreMaintRepository;
import com.linkbit.beidou.domain.preMaint.VpreMaint;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2016年10月9日11:46:27
 * 预防性维修业务类
 */
@Service
public class PreMaintService extends BaseService {

    @Autowired
    VpreMaintRepository preMaintRepository;

    /**
     * @return 查询所有
     */
    public Page<VpreMaint> findAll(Pageable pageable) {

        return preMaintRepository.findAll(pageable);
    }


    public Page<VpreMaint> findByPmDescContains(String desc, Pageable pageable) {

        return preMaintRepository.findByPmDescContains(desc, pageable);
    }
}
