package com.springboot.bootstrap.controller.logincontroller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login(Model model){
        return "/customer/login";
    }
    @GetMapping("/")
    public String home(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        return "/customer/index";
    }


    @GetMapping("/about")
    public String view(){
        return "/customer/about";
    }
}
