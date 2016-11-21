package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;

import java.util.List;
import java.util.Optional;

/**
 * User Service
 */
public interface UserService {

    /**
     * Find User by Email
     *
     * @param email user email
     * @return
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Create User according to resource
     *
     * @param resource User Resource
     * @return User entity
     */
    User create(UserResource resource);

    /**
     * Update User by Email
     *
     * @param email    user email
     * @param resource user resource
     * @return Optional User
     */
    Optional<User> update(String email, UserResource resource);

    /**
     * Get current logged User
     *
     * @return
     */
    Optional<User> getCurrentUser();

    /**
     * Get all users
     *
     * @return
     */
    List<User> findAll();

    /**
     * Delete user by email
     *
     * @param email user email
     * @return Optional user
     */
    Optional<User> delete(String email);
}
