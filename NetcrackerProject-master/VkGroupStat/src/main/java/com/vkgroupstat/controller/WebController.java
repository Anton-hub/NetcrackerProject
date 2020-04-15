package com.vkgroupstat.controller;

import com.vkgroupstat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController{
    private final UserService service;

    public WebController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String index() {

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {

        return "index";
    }


    @RequestMapping("/search")
    public String getCode( @RequestParam String code){
        String token = service.userRequestHandler(code);
        System.out.println(token);
        return "search";
    }


}
