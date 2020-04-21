package com.vkgroupstat.controller;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.User;
import com.vkgroupstat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;


@Controller
public class WebController{


    private final UserService service;
    public static String USER_ID;
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
        USER_ID = service.userRequestHandler(code);
        return "search";
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
