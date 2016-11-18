package com.linkbit.beidou.controller.person;


import com.linkbit.beidou.controller.common.BaseController;
import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.domain.person.Person;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.app.ResourceService;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/person")
public class PersonController extends BaseController {
    @Autowired
    PersonService  personService;
    @Autowired
    ResourceService resourceService;

    @Autowired
    CommonDataService commonDataService;


    /**
     * 分页查询
     *
     * @param current      当前页
     * @param rowCount     每页条数
     * @param searchPhrase 查询关键字
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(@RequestParam(value = "current", defaultValue = "0") int current, @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {

        return getPageUtils().searchByService(personService, searchPhrase, 2, current, rowCount);
    }

    /**
     * 查询根节点
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Person> findAll() {
        List<Person> personList = personService.findAll();
        return personList;
    }


    /**
     * 查询根节点
     *
     * @param personId
     * @param personNo
     * @param personName
     * @param telephone
     * @param email
     * @param birthDate
     * @param status
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject update(
            @RequestParam("personId") Long personId,
            @RequestParam("personNo") String personNo,
            @RequestParam("personName") String personName,
            @RequestParam("telephone") String telephone,
            @RequestParam("email") String email,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("status") String status
    ) {

        ReturnObject returnObject = new ReturnObject();
        Person person = personService.findById(personId);
        if (person != null) {
            person.setPersonName(personName);
            person.setPersonNo(personNo);
            person.setPersonName(personName);
            person.setTelephone(telephone);
            returnObject.setResult(true);
            returnObject.setResultDesc("人员信息已更新");
            try {
                person.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(birthDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
            person.setEmail(email);
            person.setStatus(status);
            person = personService.update(person);
        }
        return returnObject;
    }


    /**
     * @param person 人员信息
     * @return 新建人员信息
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Person create(Person person) {
        return personService.save(person);
    }


    /**
     * 查询激活状态的人员信息
     *
     * @return
     */
    @RequestMapping(value = "/findActivePerson")
    @ResponseBody
    public List<Person> findActivePerson() {
        List<Person> personList = personService.findActivePerson();
        return personList;
    }


    /**
     * 根据ID查询人员信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }


    /**
     * 保存人员信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam("personNo") String personNo,
                             @RequestParam("personName") String personName,
                             @RequestParam("telephone") String telephone,
                             @RequestParam("email") String email,
                             @RequestParam("birthDate") String birthDate,
                             @RequestParam("status") String status

    ) {
        Person person = new Person();
        person.setPersonNo(personNo);
        person.setPersonName(personName);
        person.setTelephone(telephone);
        person.setEmail(email);
        try {
            person.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(birthDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        person.setStatus(status);
        person = personService.save(person);
        return commonDataService.getReturnType(person != null, "人员信息保存成功!", "人员信息保存失败!");
    }


    /**
     * @return 载入新建人员表单
     */
    @RequestMapping(value = "/loadCreateForm", method = RequestMethod.GET)
    public String loadCreateForm() {
        return "/person/create";
    }


    /**
     * 根据ID查询人员信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        boolean result = personService.delete(id);
        return commonDataService.getReturnType(result, "人员信息删除成功!", "人员信息删除失败!");

    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames) {
        List<Person> dataList = personService.findByConditions(param, 2);
        personService.setDataList(dataList);
        personService.exportExcel(request, response, docName, titles, colNames);
    }

}
