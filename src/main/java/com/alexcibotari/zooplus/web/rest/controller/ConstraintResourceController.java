package com.alexcibotari.zooplus.web.rest.controller;

import com.alexcibotari.zooplus.utils.validation.Validator;
import com.alexcibotari.zooplus.utils.validation.constraint.ConstraintDefinition;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/constraints", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConstraintResourceController {

    @GetMapping(path = "user")
    public ResponseEntity<Map<String, List<ConstraintDefinition>>> getUser() {
        return ResponseEntity.ok(Validator.extractConstraint(UserResource.class));
    }
}
