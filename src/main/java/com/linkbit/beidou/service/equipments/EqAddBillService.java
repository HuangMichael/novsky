package com.linkbit.beidou.service.equipments;

import com.linkbit.beidou.dao.equipments.EqAddBillRepository;
import com.linkbit.beidou.domain.equipments.EqAddBill;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备新置申请单业务类
 */
@Service
public class EqAddBillService extends BaseService {


    @Autowired
    EqAddBillRepository eqAddBillRepository;

   /* @Autowired
    VEqAddBillRepository vEqAddBillRepository;*/


    /**
     * @param eqName
     * @param pageable 分页
     * @return 按照配件名称模糊查询分页查询
     */
   /* public Page<VEqAddBill> findByEqNameContaining(String eqName, Pageable pageable) {
        return vEqAddBillRepository.findByEqNameContaining(eqName, pageable);
    }*/


    /**
     * @return 查询所有
     */
    public EqAddBill findById(Long id) {
        return eqAddBillRepository.findById(id);
    }


    /**
     * @return 查询所有
     */
    public EqAddBill save(EqAddBill budgetBill) {

        return eqAddBillRepository.save(budgetBill);
    }


    /**
     * @param id 根据id删除 删除成功返回true
     * @return
     */
    public boolean delete(Long id) {
        if (id != null) {
            eqAddBillRepository.delete(id);
        }
        return eqAddBillRepository.findById(id) == null;
    }


    /**
     * @return 查询所有的id
     */
    public List<Long> findAllIds() {
        List<Long> ids = eqAddBillRepository.findAllIds();
        return ids;
    }


    /**
     * @param eid 设备id
     * @return 根据设备id查询设备的更新历史
     */
    /*public List<EqAddBill> getUpdateHistoryById(Long eid) {
        return eqAddBillRepository.findByEquipmentsId(eid);
    }*/


}
