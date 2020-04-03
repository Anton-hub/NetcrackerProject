package com.vkgroupstat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping
    public String index() {

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {

        return "index";
    }
    @GetMapping("/search")
    public String getSearch() {

        return "search";
    }
}
