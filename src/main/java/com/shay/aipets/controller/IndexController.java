package com.shay.aipets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/download")
    public String index(){
        return "index.html";
    }
}
