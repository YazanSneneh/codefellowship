package com.codefellowship.codefellowship.controller;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ControllerApplicationUser {

     @Autowired
     ApplicationUserRepository applicationUserRepository;

     @Autowired
     PasswordEncoder passwordEncoder;;

    @Autowired
    PostRepository postRepository;


    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }
    @GetMapping("/login")
    public String getLoginPage(){ return "login.html";}


    @PostMapping("/signup")
    public RedirectView createUser(@RequestParam(value = "username")String username,
                                   @RequestParam(value = "password")String password,
                                   @RequestParam(value = "firstName")String fname,
                                   @RequestParam(value = "lastName")String lname,
                                   @RequestParam(value = "dateOfBirth")String date,
                                   @RequestParam(value = "bio")String bio ){

       // encode password before adding it to database
        String eyncPass = passwordEncoder.encode(password);
        ApplicationUser user = new ApplicationUser( eyncPass,username,fname,lname,date,bio);

        applicationUserRepository.save(user);

        // this code when i sign up, it will redirect user to profile directly.
        //  if I want user to login first then i can remove it.
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/loggedInUser");
    }
    @GetMapping("/loggedInUser")
    public String getLoggedInHome(Principal p, Model m){
        m.addAttribute("loggedUser", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        return "loggedInHome.html";
    }

    @PostMapping ("/login")
    public String loggedIn(){

        return "loggedInHome.html";
    }

    @GetMapping("/profile/{id}")
    public String getUserProfilePage(Principal principal ,Model model , @PathVariable(value = "id") Integer id){
        ApplicationUser user = applicationUserRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("logged", ((UsernamePasswordAuthenticationToken)principal).getPrincipal());
        return "viewOneProfile.html";
    }

    @GetMapping("/profile")
    // principal : instance created by auth on signup, and i am passing it here so i login to specific user
        public String getUserProfilePage(Principal principal, Model model){
        ApplicationUser user= (ApplicationUser)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
//       model.addAttribute("posts", user.getPosts());

        model.addAttribute("user", applicationUserRepository.findById(user.getId()).get());
        return "profile.html";
    }
}