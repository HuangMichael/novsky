package com.linkbit.beidou.service.department;

import com.linkbit.beidou.dao.department.DepartmentRepository;
import com.linkbit.beidou.domain.department.Department;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 */
@Service
public class DepartmentService extends BaseService {

    @Autowired
    DepartmentRepository departmentRepository;

    public String getCodeByParent(Department department) {
        String code = "";
        if (department != null) {
            code += department.getDeptNum();
            int childrenSize = findByParentId(department.getId()).size();
            for (int i = 1; i < 3 - (childrenSize + "").length(); i++) {
                code += "0";
            }
            code += (childrenSize + 1);
        } else {
            code = "001";
        }
        return code;
    }

    /**
     * @return 查询所有的部门信息
     */
    List<Department> findAll() {
        return departmentRepository.findAll();
    }


    /**
     * @param pid 上级部门编号
     * @return 查询所有的下属部门信息
     */
    List<Department> findByParentId(Long pid) {
        Department parent = departmentRepository.findById(pid);
        return departmentRepository.findByParentAndStatus(parent, "1");
    }
}
