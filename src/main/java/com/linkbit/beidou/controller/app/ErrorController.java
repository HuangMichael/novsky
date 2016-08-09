package com.linkbit.beidou.controller.app;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/4/22.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/")
    public String list() {
        return "/error";
    }

}
