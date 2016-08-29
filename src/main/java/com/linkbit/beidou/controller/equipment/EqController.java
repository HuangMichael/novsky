package com.linkbit.beidou.controller.equipment;


import com.linkbit.beidou.controller.common.BaseController;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *  可分页控制器
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/eq")
public class EqController extends BaseController {


    /*@Autowired EquipmentsRepository equipmentsRepository;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    HttpEntity<PagedResources<Equipments>> findAll(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Equipments> equipmentsList = equipmentsRepository.findAll(pageable);
        return new ResponseEntity(assembler.toResources(equipmentsList), HttpStatus.OK);
    }*/



}
