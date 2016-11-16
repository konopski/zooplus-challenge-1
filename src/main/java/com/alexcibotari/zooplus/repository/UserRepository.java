package com.alexcibotari.zooplus.repository;


import com.alexcibotari.zooplus.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByUserName(String userName);

    @Modifying
    Optional<User> deleteByUserName(String userName);
}
