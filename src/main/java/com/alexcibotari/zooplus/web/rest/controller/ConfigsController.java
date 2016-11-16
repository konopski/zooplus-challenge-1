
package com.alexcibotari.zooplus.web.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/configs")
public class ConfigsController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

}
