package com.vkgroupstat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vkgroupstat.service.UserService;

@Controller
public class WebController{

    public static Integer USER_ID;

    private final UserService service;
    @Autowired
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
    @GetMapping("/default")
    public String getDef() {
        return "default";
    }


    @RequestMapping("/search")
    public String getSearch() {
        return "search";
    }
    @RequestMapping("/dashboard")
    public String getDash() {
        return "dashboard";
    }
    @RequestMapping("/dash")
    public String getCode( @RequestParam String code){
        USER_ID = service.userRequestHandler(code);
        return "dashboard";
    }

    @GetMapping("/send")
    public String getSend() {
        return "send";
    }
    
    @GetMapping("/history")
    public String getHistory() {

        return "history";
    }
}