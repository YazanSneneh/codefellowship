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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<ApplicationUser> list = new ArrayList<>();

        applicationUserRepository.findAll().forEach(list::add);
         m.addAttribute("users",list);
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

    @PutMapping("/profile/{id}")
    public RedirectView updateUser(@PathVariable("id") int id,
                                   @RequestParam(value = "username")String username,
                                   @RequestParam(value = "password")String password,
                                   @RequestParam(value = "firstName")String fname,
                                   @RequestParam(value = "lastName")String lname,
                                   @RequestParam(value = "dateOfBirth")String date,
                                   @RequestParam(value = "bio")String bio,Model model, Principal principal){
       ApplicationUser user =  applicationUserRepository.findByUsername(username);
        System.out.println(user.getUsername());
        return new RedirectView("/profile");
    }

    @PostMapping("/feed")
    public RedirectView addNewFollow(Principal p, @RequestParam(value ="id") Integer id){
        ApplicationUser user = (ApplicationUser) ((UsernamePasswordAuthenticationToken)p).getPrincipal();
        ApplicationUser friend = applicationUserRepository.findById(id).get();

        if(!user.getFriendOf().contains(friend) ){
            user.addFriend(friend);
            applicationUserRepository.save(user);
        }


        return new RedirectView("/feed");
    }


    @GetMapping("/feed")
    public String feedPage(Principal p, Model m){
        ApplicationUser user = (ApplicationUser) ((UsernamePasswordAuthenticationToken)p).getPrincipal();

        m.addAttribute("user", user);
        return "feed.html";
    }

}


    /*
    <section>
    <h3>Update your profile</h3>
    <form  th:if="${isAllowedToEdit == true}" th:action="'/profile/'+${user.id} +'?_method=PUT'" method="POST">
        <label for="username">User name</label>
        <input id="username" name="username" type="text" th:value="${user.username}"> <br>

        <label for="password">password</label>
        <input id="password" name="password" type="text" th:value="${user.password}"> <br>

        <label for="firstName">first Name</label>
        <input id="firstName" name="firstName" type="text" th:value="${user.username}"> <br>

        <label for="lastName">last Name</label>
        <input id="lastName" name="lastName" type="text" th:value="${user.lastName}"> <br>

        <label for="dateOfBirth">date Of Birth</label>
        <input id="dateOfBirth" name="dateOfBirth" type="date" th:value="${user.dateOfBirth}"> <br>

        <label for="bio">bio</label>
        <input id="bio" name="bio" type="text" th:value="${user.bio}"> <br>

        <button type="submit">Update</button>
    </form>
</section>
    */