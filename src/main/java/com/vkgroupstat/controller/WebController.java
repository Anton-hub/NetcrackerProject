package com.vkgroupstat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WebController{
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

//    @GetMapping("/result")
//    public ModelAndView result(String[] json) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("result");
//        mav.addObject("json", json);
//        return mav;
//    }

}
