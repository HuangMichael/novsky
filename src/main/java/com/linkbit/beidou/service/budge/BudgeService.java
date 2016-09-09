package com.linkbit.beidou.service.budge;

import com.linkbit.beidou.dao.budget.BudgetBillRepository;
import com.linkbit.beidou.dao.equipments.EquipmentsRepository;
import com.linkbit.beidou.dao.equipments.VEqRepository;
import com.linkbit.beidou.dao.outsourcingUnit.OutsourcingUnitRepository;
import com.linkbit.beidou.domain.budget.BudgetBill;
import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.service.locations.LocationsService;
import com.linkbit.beidou.utils.CommonStatusType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2016/5/4.
 * 设备台账业务类
 */
@Service
public class BudgeService extends BaseService {


    @Autowired
    BudgetBillRepository budgetBillRepository;


    /**
     * @param pageable
     * @return 分页查询
     */
    public Page<BudgetBill> findAll(Pageable pageable) {
        return budgetBillRepository.findAll(pageable);
    }

    /**
     * @return 查询所有
     */
    public List<BudgetBill> findAll() {
        return budgetBillRepository.findAll();
    }
}
