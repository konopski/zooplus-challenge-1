
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
import java.util.stream.Collectors;

/**
 * REST API Controller for Exchange Resource
 */
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

    /**
     * GET /currencies : get all currencies
     *
     * @return the ResponseEntity with status 200 (OK) and with body all currencies
     */
    @GetMapping(path = "currencies")
    public ResponseEntity<?> currencies() {
        return ResponseEntity.ok(service.getCurrencies());
    }

    /**
     * GET /convert/:currency/:amount/:date : convert currency amount at specific date
     *
     * @param currency in ISO format
     * @param amount   amount of money
     * @param date     exchange date
     * @return the ResponseEntity with status 200 (OK) and with body exchange
     */
    @GetMapping(path = "convert/{currency}/{amount}/{date}")
    public ResponseEntity<?> convert(
            @PathVariable String currency,
            @PathVariable BigDecimal amount,
            @PathVariable String date) {
        return service.convert(currency, amount, date).map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * GET / : Last 10 Conversion
     *
     * @return the ResponseEntity with status 200 (OK) and with body all exchanges
     */
    @GetMapping
    public ResponseEntity<Resources<ExchangeResource>> list() {
        Link link = entityLinks.linkToCollectionResource(ExchangeResource.class);
        Resources<ExchangeResource> resources = new Resources<>(resourceAssembler.toResources(service.findAllForCurrentUser().stream().sorted((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate())).collect(Collectors.toList())), link);
        return ResponseEntity.ok(resources);
    }

    /**
     * GET /:id : get Exchange Resource by ID
     *
     * @return the ResponseEntity with status 200 (OK) and with body exchange
     */
    @GetMapping("{id}")
    public ResponseEntity<ExchangeResource> one(@PathVariable Long id) {
        return toResourceResponse(service.findOne(id));
    }

    /**
     * Resource Assembler Method. Convert Entity object into Resource
     *
     * @param entity
     * @return
     */
    private ResponseEntity<ExchangeResource> toResourceResponse(Optional<Exchange> entity) {
        return entity.map(e -> ResponseEntity.ok(resourceAssembler.toResource(e)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
