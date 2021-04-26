package com.codefellowship.codefellowship.repository;

import com.codefellowship.codefellowship.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository <Post, Integer>{
}
