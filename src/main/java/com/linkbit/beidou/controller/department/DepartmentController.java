package com.linkbit.beidou.controller.department;


import com.linkbit.beidou.dao.department.DepartmentRepository;
import com.linkbit.beidou.dao.person.PersonRepository;
import com.linkbit.beidou.domain.department.Department;
import com.linkbit.beidou.domain.person.Person;
import com.linkbit.beidou.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/department")
@SessionAttributes({"departmentList", "personList"})
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Department> departmentList = departmentRepository.findAll();
        modelMap.put("departmentList", departmentList);
        Department department = departmentList.get(0);
        modelMap.put("department", department);
        modelMap.put("departmentList", departmentList);
        List<Person> personList = personRepository.findByStatus("1");
        modelMap.put("personList", personList);
        return "/department/list";
    }

    /**
     * 查询根节点
     */
    @RequestMapping(value = "/findNodeByParentId")
    @ResponseBody
    public List<Department> findNodeByParentId() {
        List<Department> departmentList = departmentRepository.findNodeByParentId();
        return departmentList;
    }
    /**
     * 查询根节点
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        String url = "/department";
        if (id != 0) {
            url += "/detail";
        } else {
            url += "/list/";
        }
        Department department = departmentRepository.findById(id);
        modelMap.put("department", department);
        return url;
    }
    /**
     * 查询根节点
     */
    @RequestMapping(value = "/findAllBySort")
    @ResponseBody
    public List<Department> findAllBySort() {
        List<Department> departmentList = departmentRepository.findAllBySort();
        return departmentList;
    }

    /**
     * 查询根节点
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Department> findAll() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    /**
     * 查询根节点
     */
    @RequestMapping(value = "/create/{id}")
    public String create(@PathVariable("id") Long id, ModelMap modelMap) {
        Department parent = departmentRepository.findById(id);
        Department newObj = new Department();
        newObj.setDeptNum(departmentService.getCodeByParent(parent));
        //加载上级列表
        List<Department> departmentList = departmentRepository.findAll();
        modelMap.put("departmentList", departmentList);
        modelMap.put("department", newObj);
        modelMap.put("parent", parent);
        return "/department/create";
    }

    /**
     * 保存部门信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Department save(Department department) {
        return departmentRepository.save(department);
    }



    /**
     * 保存部门信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
        Department department = departmentRepository.findById(id);
        if (department != null) {
            departmentRepository.delete(departmentRepository.findById(id));
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/select")
    public String select() {

        return "/department/select";
    }


   /* *//**
     * 部门选择人员信息
     *//*
    @RequestMapping(value = "/selectUsers", method = RequestMethod.GET)
    @ResponseBody
    public Boolean selectUsers(@RequestParam("personId") long personId, @RequestParam("departmentId") Long departmentId) {
        Person person = personRepository.findById(personId);
        person.setDepartment(departmentRepository.findById(departmentId));
        personRepository.save(person);
        return true;
    }*/
}
