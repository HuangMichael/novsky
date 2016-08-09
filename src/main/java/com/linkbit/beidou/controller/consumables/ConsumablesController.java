package com.linkbit.beidou.controller.consumables;


import com.linkbit.beidou.domain.app.resoure.Resource;
import com.linkbit.beidou.domain.consumables.Consumables;
import com.linkbit.beidou.domain.consumables.DrawList;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.consumables.ConsumablesService;
import com.linkbit.beidou.service.consumables.DrawListService;
import com.linkbit.beidou.utils.SessionUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2015/12/23 0023.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/consumables")
public class ConsumablesController {


    Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumablesController.class);

    @Autowired
    ConsumablesService consumablesService;

    @Autowired
    DrawListService drawListService;

    @RequestMapping(value = "/list/{type}")
    public String list(@PathVariable("type") String type, ModelMap modelMap) {
        List<Consumables> consumablesList = consumablesService.findByType(type);
        modelMap.put("consumablesList", consumablesList);
        return "/consumables/list" + type;
    }


    /**
     * 查询根节点
     */
    @RequestMapping(value = "/create/{id}")
    public String create(@PathVariable("id") Long id, ModelMap modelMap) {
        /*Resource parent = consumablesService.findById(id);
        Resource newObj = new Resource();
        newObj.setParent(parent);
        //加载上级列表
        List<Resource> resourceList = resourceService.findAll();
        modelMap.put("resourceList", resourceList);
        modelMap.put("resource", newObj);
        modelMap.put("parent", parent);*/
        //查询出所有的设备分类
        return "/resource/create";
    }


    /**
     * 查询根节点
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        String url = "/resource";
        /*if (id != null) {
            url += "/detail";
        } else {
            url += "/list/";
        }
        Resource resource = resourceService.findById(id);
        List<Resource> resourceList = new ArrayList<Resource>();
        if (resource != null) {
            resourceList = resourceService.findByParent(resource);
        }
        modelMap.put("resource", resource);
        modelMap.put("resourceList", resourceList);*/
        return url;
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Resource findById(@PathVariable("id") Long id) {
       /* Resource resource = resourceService.findById(id);
        return resource;*/
        return null;
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/findByType/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<Consumables> findByType(@PathVariable("type") String type) {

        return consumablesService.findByType(type);
    }

    /**
     * 保存资源信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Consumables save(@RequestParam("type") String type, Consumables consumables) {
        consumables.setConsNo(new Date().getTime() + "");
        consumables.setType(type);
        Consumables saved = consumablesService.save(consumables);
        return saved;
    }


    /**
     * 删除位置信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean delete(@PathVariable("id") Long id) {
       /* boolean hasChildren = resourceService.hasChildren(id);
        if (!hasChildren) {
            resourceService.delete(id);
            return true;
        } else {
            return false;
        }*/

        return null;
    }


    /**
     * @param id       易耗品id
     * @param drawList 领取记录对象
     * @param request  当前的request请求对象
     * @return
     */
    @RequestMapping(value = "/draw/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject draw(@PathVariable("id") Long id, @RequestParam("type") String type, DrawList drawList, HttpServletRequest request) {
        drawList.setHandler(SessionUtil.getCurrentUser(request).getPerson().getPersonName());
        drawList.setHandleTime(new Date());
        drawList.setType(type);
       /* drawList.setConsumables(consumablesService.findById(id));*/
        drawList = drawListService.save(drawList);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(drawList != null);
        returnObject.setResultDesc("易耗品领取成功");

        return returnObject;
    }
}
