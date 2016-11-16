
package com.alexcibotari.zooplus.web.rest.controller;


import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.service.ExchangeService;
import com.alexcibotari.zooplus.web.rest.assembler.ExchangeResourceAssembler;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/exchanges", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(ExchangeResource.class)
public class ExchangeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityLinks entityLinks;
    @Autowired
    private ExchangeService service;
    @Autowired
    private ExchangeResourceAssembler resourceAssembler;

    @GetMapping(path = "currencies")
    public ResponseEntity<?> currencies() {
        return ResponseEntity.ok(service.getCurrencies());
    }

    @GetMapping(path = "convert/{currency}/{amount}/{date}")
    public ResponseEntity<?> convert(
            @PathVariable String currency,
            @PathVariable BigDecimal amount,
            @PathVariable String date) {
        return service.convert(currency, amount, date).map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<Resources<ExchangeResource>> list() {
        Link link = entityLinks.linkToCollectionResource(ExchangeResource.class);
        Resources<ExchangeResource> resources = new Resources<>(resourceAssembler.toResources(service.findAllForCurrentUser()), link);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExchangeResource> one(@PathVariable Long id) {
        return toResourceResponse(service.findOne(id));
    }

    private ResponseEntity<ExchangeResource> toResourceResponse(Optional<Exchange> entity) {
        return entity.map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
