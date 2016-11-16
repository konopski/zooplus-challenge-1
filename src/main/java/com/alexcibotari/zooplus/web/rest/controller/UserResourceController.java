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

    @GetMapping
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Resources<UserResource>> list() {
        Link link = entityLinks.linkToCollectionResource(UserResource.class);
        Resources<UserResource> resources = new Resources<>(resourceAssembler.toResources(service.findAll()), link);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("{userName}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> one(@PathVariable String userName) {
        return toResourceResponse(service.findOneByUserName(userName));
    }

    @PostMapping
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> create(@RequestBody UserResource resource) throws URISyntaxException {
        if (service.findOneByUserName(resource.getUserName()).isPresent()) {
            return new ResponseEntity<>(new ResourceError("UserName already in use"), HttpStatus.CONFLICT);
        } else {
            resource = resourceAssembler.toResource(service.create(resource));
            return ResponseEntity.created(new URI(resource.getId().getHref())).body(resource);
        }
    }

    @PutMapping("{userName}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> update(@PathVariable String userName, @RequestBody UserResource resource) {
        return toResourceResponse(service.update(userName, resource));
    }

    @DeleteMapping("{userName}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> delete(@PathVariable String userName) {
        return toResourceResponse(service.delete(userName));
    }

    private ResponseEntity<UserResource> toResourceResponse(Optional<User> entity) {
        return entity.map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{userName}/password")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> password(@PathVariable String userName) {
        //TODO change logic
        return null;
    }

    @PutMapping("{userName}/password/reset")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserResource> passwordReset(@PathVariable String userName) {
        //TODO reset logic
        return null;
    }
}
