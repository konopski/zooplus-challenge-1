package com.alexcibotari.zooplus.web.rest.assembler;

import com.alexcibotari.zooplus.domain.User;
import com.alexcibotari.zooplus.web.rest.controller.UserResourceController;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    @Autowired
    private EntityLinks entityLinks;

    public UserResourceAssembler() {
        super(UserResourceController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User entity) {
        UserResource resource = createResourceWithId(entity.getEmail(), entity);
        resource.add(entityLinks.linkFor(UserResource.class).slash(entity.getEmail()).slash("authorities").withRel("authorities"));
        return resource;
    }

    @Override
    protected UserResource instantiateResource(User entity) {
        UserResource resource = new UserResource();
        resource.setEmail(entity.getEmail());
        resource.setBirthDay(entity.getBirthDay());
        resource.getContact().setCity(entity.getContact().getCity());
        resource.getContact().setCountry(entity.getContact().getCountry());
        resource.getContact().setStreet(entity.getContact().getStreet());
        resource.getContact().setZip(entity.getContact().getZip());
        resource.setCreatedDate(entity.getCreatedDate());
        resource.setLastModifiedDate(entity.getLastModifiedDate());
        return resource;
    }
}
