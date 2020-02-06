package com.opencode.managment.controller;

import com.opencode.bullcow.entity.User;
import com.opencode.managment.service.UserService;
import com.opencode.managment.util.HTTPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private UserService service;

    @Autowired
    public void setService(UserService service){
        this.service = service;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        return "register.html";
    }

    @PostMapping("/register")
    public @ResponseBody HTTPResponse register(@RequestBody User entity){
        return service.register(entity);
    }

}
