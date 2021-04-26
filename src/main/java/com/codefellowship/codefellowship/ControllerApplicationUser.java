package com.codefellowship.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ControllerApplicationUser {

     @Autowired
     ApplicationUserRepository applicationUserRepository;

     @Autowired private
     BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView createUser(@RequestParam(value = "username")String username,
                                   @RequestParam(value = "password")String password,
                                   @RequestParam(value = "firstName")String fname,
                                   @RequestParam(value = "lastName")String lname,
                                   @RequestParam(value = "dateOfBirth")String date,
                                   @RequestParam(value = "bio")String bio ){

        String eyncPass = bCryptPasswordEncoder.encode(password);
        ApplicationUser user = new ApplicationUser( eyncPass,username,fname,lname,date,bio);
        applicationUserRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
       SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/profile")
    // principal : instance created by auth, and i am passing it here so i login to specific user
    public String getUserProfilePage(Principal principal, Model model){

        // add the user data to the model so i can inject it in thymeleaf
        model.addAttribute("user", ((UsernamePasswordAuthenticationToken)principal).getPrincipal());
        return "profile.html";
    }
}
