package com.linkbit.beidou.service.budge;

import com.linkbit.beidou.dao.budget.BudgetBillRepository;
import com.linkbit.beidou.dao.budget.EcBudgetBillRepository;
import com.linkbit.beidou.dao.budget.VbudgetBillRepository;
import com.linkbit.beidou.dao.budget.VecbudgetBillRepository;
import com.linkbit.beidou.dao.locations.VlocationsRepository;
import com.linkbit.beidou.domain.EcBudget.EcBudgetBill;
import com.linkbit.beidou.domain.EcBudget.VEcBudgetBill;
import com.linkbit.beidou.domain.budget.BudgetBill;
import com.linkbit.beidou.domain.budget.VbudgetBill;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2016/5/4.
 * 设备台账业务类
 */
@Service
public class EcBudgeService extends BaseService {


    @Autowired
    EcBudgetBillRepository ecBudgetBillRepository;


    @Autowired
    VecbudgetBillRepository vecbudgetBillRepository;


    @Autowired
    VlocationsRepository vlocationsRepository;


    /**
     * @param pageable
     * @return 分页查询
     */
    public Page<VEcBudgetBill> findAllV(Pageable pageable) {

        return vecbudgetBillRepository.findAll(pageable);
    }


    /**
     * @param pageable
     * @return 分页查询
     */
    public Page<EcBudgetBill> findAll(Pageable pageable) {
        return ecBudgetBillRepository.findAll(pageable);
    }

    /**
     * @return 查询所有
     */
    public List<EcBudgetBill> findAll() {
        return ecBudgetBillRepository.findAll();
    }


    /**
     * @return 查询所有
     */
    public EcBudgetBill findById(Long id) {
        return ecBudgetBillRepository.findById(id);
    }


    /**
     * @return 查询所有
     */
    public List<VEcBudgetBill> findAllV() {
        return vecbudgetBillRepository.findAll();
    }


    /**
     * @return 查询所有
     */
    public EcBudgetBill save(EcBudgetBill  ecBudgetBill) {
        ecBudgetBill.setApplyDate(new Date());
        return ecBudgetBillRepository.save(ecBudgetBill);
    }


}
