package com.linkbit.beidou.controller.person;


import com.linkbit.beidou.domain.person.Person;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;




    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap) {
        List<Person> personList = personService.findAll();
        modelMap.put("personList", personList);
        return "/person/list";
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

        ReturnObject returnObject = new ReturnObject();
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
        returnObject.setResult(person!=null);
        returnObject.setResultDesc("人员信息保存成功!");
        return returnObject;
    }


    /**
     * @return 载入新建人员表单
     */
    @RequestMapping(value = "/loadCreateForm", method = RequestMethod.GET)
    public String loadCreateForm() {
        return "/person/create";
    }

}
