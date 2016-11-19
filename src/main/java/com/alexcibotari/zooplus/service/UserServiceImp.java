package com.alexcibotari.zooplus.service;


import com.alexcibotari.zooplus.domain.Authority;
import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.repository.AuthorityRepository;
import com.alexcibotari.zooplus.repository.UserRepository;
import com.alexcibotari.zooplus.security.AuthoritiesConstants;
import com.alexcibotari.zooplus.security.SecurityUtils;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public User create(UserResource resource) {
        User user = new User();
        user.setEmail(resource.getEmail());
        user.setEnabled(resource.getEnabled());
        user.setPassword(passwordEncoder.encode(resource.getPassword()));
        user.setBirthDay(resource.getBirthDay());
        user.getContact().setStreet(resource.getContact().getStreet());
        user.getContact().setZip(resource.getContact().getZip());
        user.getContact().setCity(resource.getContact().getCity());
        user.getContact().setCountry(resource.getContact().getCountry());

        Set<Authority> authorities = new HashSet<>();
        if (resource.getAuthorities().size() == 0) {
            resource.getAuthorities().add(AuthoritiesConstants.USER);
        }
        resource.getAuthorities().forEach(authority -> authorityRepository.findOneByName(authority).map(authorities::add));
        user.setAuthorities(authorities);

        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> update(String email, UserResource resource) {
        return userRepository.findOneByEmail(email)
                .map(entity -> {
                    entity.setEnabled(resource.getEnabled());
                    Set<Authority> authorities = new HashSet<>();
                    resource.getAuthorities().stream().forEach(authority -> authorityRepository.findOneByName(authority).map(authorities::add));
                    entity.setAuthorities(authorities);
                    return userRepository.save(entity);
                });
    }

    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public Optional<User> getCurrentUser() {
        return userRepository.findOneByEmail(SecurityUtils.getCurrentUserName());
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public Optional<User> delete(String email) {
        return userRepository.deleteByEmail(email);
    }
}
