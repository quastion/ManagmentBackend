package com.opencode.managment.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @PostMapping("/principal")
    public @ResponseBody
    Authentication currentUserName(Authentication authentication) {
        return authentication;
    }
}