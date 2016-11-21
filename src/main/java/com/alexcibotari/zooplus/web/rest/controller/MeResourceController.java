
package com.alexcibotari.zooplus.web.rest.controller;


import com.alexcibotari.zooplus.service.UserService;
import com.alexcibotari.zooplus.web.rest.assembler.UserResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller for personal information
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeResourceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserResourceAssembler userResourceAssembler;

    /**
     * GET /me .
     *
     * @return Current user profile
     */
    @GetMapping(path = "me")
    public ResponseEntity<?> me() {
        return userService.getCurrentUser().map(user -> ResponseEntity.ok(userResourceAssembler.toResource(user))).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
