package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findOneByEmail(String email);

    User create(UserResource resource);

    Optional<User> update(String email, UserResource resource);

    Optional<User> getCurrentUser();

    List<User> findAll();

    Optional<User> delete(String email);
}
