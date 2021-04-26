package com.codefellowship.codefellowship.repository;

import com.codefellowship.codefellowship.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser,Integer> {
    public ApplicationUser findByUsername(String userName);
}
