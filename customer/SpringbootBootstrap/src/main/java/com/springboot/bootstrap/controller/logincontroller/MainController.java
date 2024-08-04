package com.springboot.bootstrap.controller.logincontroller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login(Model model){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return "/pages/login";
    }
    @GetMapping("/")
    public String home(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return "/pages/giao_dich";
    }

//    @GetMapping("/home")
//    public String thongKe() {
//        return "/pages/landing_page";
//    }
}
