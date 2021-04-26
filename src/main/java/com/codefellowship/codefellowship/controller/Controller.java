package com.codefellowship.codefellowship.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String getHomePage(Model model, Principal principal) {

        return "homepage.html";
    }
}
