package com.linkbit.beidou.service.budge;

import com.linkbit.beidou.dao.budget.VecbudgetBillRepository;
import com.linkbit.beidou.domain.EcBudget.VEcBudgetBill;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.DateUtils;
import com.linkbit.beidou.utils.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2016/11/21.
 * 易耗品查询
 */
@Service
public class EcBudgeSearchService extends BaseService implements Searchable {

    @Autowired
    VecbudgetBillRepository vecbudgetBillRepository;

    /**
     * @param searchPhrase
     * @param paramsSize
     * @return
     */
    public List<VEcBudgetBill> findByConditions(String searchPhrase, int paramsSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramsSize);
        Date beginDate = null, endDate = null;
        if (array[0].isEmpty()) {
            beginDate = new Date();
        } else {
            try {
                beginDate = DateUtils.convertStr2Date(array[0], "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (array[1].isEmpty()) {
            endDate = new Date();
        } else {
            try {
                endDate = DateUtils.convertStr2Date(array[1], "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vecbudgetBillRepository.findByApplyDateBetweenAndEcnameContainsAndLocationContains(beginDate, endDate, array[2], array[3]);
    }

    /**
     * @param searchPhrase
     * @param paramsSize
     * @return
     */
    public Page<VEcBudgetBill> findByConditions(String searchPhrase, int paramsSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramsSize);
        Date beginDate = null, endDate = null;
        if (array[0].isEmpty()) {
            beginDate = new Date();
        } else {
            try {
                beginDate = DateUtils.convertStr2Date(array[0], "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (array[1].isEmpty()) {
            endDate = new Date();
        } else {
            try {
                endDate = DateUtils.convertStr2Date(array[1], "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vecbudgetBillRepository.findByApplyDateBetweenAndEcnameContainsAndLocationContains(beginDate, endDate, array[2], array[3], pageable);
    }

}
