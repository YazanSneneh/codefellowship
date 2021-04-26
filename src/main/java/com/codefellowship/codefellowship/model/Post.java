package com.codefellowship.codefellowship.model;


import com.codefellowship.codefellowship.model.ApplicationUser;

import javax.persistence.*;

@Entity
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body ;
    private String createdAt;

    @ManyToOne
    private ApplicationUser user;

    public Post(){}

    public Post(String body, String createdAt,ApplicationUser user ) {
        this.body = body;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}