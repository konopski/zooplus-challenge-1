package com.alexcibotari.zooplus.web.rest.controller;


import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.security.AuthoritiesConstants;
import com.alexcibotari.zooplus.service.UserService;
import com.alexcibotari.zooplus.web.rest.assembler.UserResourceAssembler;
import com.alexcibotari.zooplus.web.rest.errors.ResourceError;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST API Controller for User Resource
 */
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ExposesResourceFor(UserResource.class)
public class UserResourceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private UserService service;

    @Autowired
    private UserResourceAssembler resourceAssembler;

    /**
     * GET / : Get list of all users
     *
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Resources<UserResource>> list() {
        Link link = entityLinks.linkToCollectionResource(UserResource.class);
        Resources<UserResource> resources = new Resources<>(resourceAssembler.toResources(service.findAll()), link);
        return ResponseEntity.ok(resources);
    }

    /**
     * GET /:email : Get user by Email
     *
     * @param email
     * @return the ResponseEntity with status 200 (OK) and with body user
     */
    @GetMapping("{email}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> one(@PathVariable String email) {
        return toResourceResponse(service.findOneByEmail(email));
    }

    /**
     * POST / : Create user
     *
     * @param resource
     * @return the ResponseEntity with status 200 (OK) and with body user
     * @throws URISyntaxException
     */
    @PostMapping
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> create(@RequestBody UserResource resource) throws URISyntaxException {
        if (service.findOneByEmail(resource.getEmail()).isPresent()) {
            return new ResponseEntity<>(new ResourceError("Email already in use"), HttpStatus.CONFLICT);
        } else {
            resource = resourceAssembler.toResource(service.create(resource));
            return ResponseEntity.created(new URI(resource.getId().getHref())).body(resource);
        }
    }

    /**
     * PUT /:email : Update user by Email
     *
     * @param email    user email
     * @param resource new user object
     * @return the ResponseEntity with status 200 (OK) and with body user
     */
    @PutMapping("{email}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> update(@PathVariable String email, @RequestBody UserResource resource) {
        return toResourceResponse(service.update(email, resource));
    }

    /**
     * DELETE /:email : Delete user by email
     *
     * @param email user email
     * @return the ResponseEntity with status 200 (OK) and with body user
     */
    @DeleteMapping("{email}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> delete(@PathVariable String email) {
        return toResourceResponse(service.delete(email));
    }

    private ResponseEntity<UserResource> toResourceResponse(Optional<User> entity) {
        return entity.map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{email}/password")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> password(@PathVariable String email) {
        //TODO change logic
        return null;
    }

    @PutMapping("{email}/password/reset")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> passwordReset(@PathVariable String email) {
        //TODO reset logic
        return null;
    }
}
