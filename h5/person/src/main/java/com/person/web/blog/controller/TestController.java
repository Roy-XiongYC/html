package com.person.web.blog.controller;

import com.person.framework.utils.ComputerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("getComputer")
    public void getComputer(){
        ComputerUtil.all();
        ComputerUtil.Config();
        ComputerUtil.getConfig();
    }

}
