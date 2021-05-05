package com.lenovo.feizai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/jump")
public class JumpController {

    @RequestMapping("/tomain")
    public String tomain(){
        return "main";
    }

    @RequestMapping("/tochangepassword")
    public String topassword(){
        return "changepassword";
    }

    @RequestMapping("/login")
    public String tologin(){
        return "login";
    }
}
