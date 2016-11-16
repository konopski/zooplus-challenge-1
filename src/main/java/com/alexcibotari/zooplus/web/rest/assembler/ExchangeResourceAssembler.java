package com.alexcibotari.zooplus.web.rest.assembler;

import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.web.rest.controller.ExchangeController;
import com.alexcibotari.zooplus.web.rest.resource.ExchangeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class ExchangeResourceAssembler extends ResourceAssemblerSupport<Exchange, ExchangeResource> {

    @Autowired
    private EntityLinks entityLinks;

    public ExchangeResourceAssembler() {
        super(ExchangeController.class, ExchangeResource.class);
    }

    @Override
    public ExchangeResource toResource(Exchange entity) {
        ExchangeResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected ExchangeResource instantiateResource(Exchange entity) {
        ExchangeResource resource = new ExchangeResource();
        resource.setCurrency(entity.getCurrency());
        resource.setAmount(entity.getAmount());
        resource.setDate(entity.getDate());
        resource.setResult(entity.getResult());
        resource.setCreatedDate(entity.getCreatedDate());
        resource.setLastModifiedDate(entity.getLastModifiedDate());
        return resource;
    }
}
