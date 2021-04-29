package com.codefellowship.codefellowship.controller;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/createpost")
    public RedirectView addNewPost(@RequestParam String body,
                                    Principal principal){
        Date date = new Date();
        String timestamp =  date.toString();

        ApplicationUser user = (ApplicationUser)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Post post = new Post(body, timestamp,user);

        postRepository.save(post);

        return new RedirectView("/profile");
    }
}