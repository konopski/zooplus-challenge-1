package com.alexcibotari.zooplus.web.rest.controller;

import com.alexcibotari.zooplus.service.CountryService;
import com.alexcibotari.zooplus.service.UserService;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping
    public String form(UserResource userResource) {
        return "register";
    }

    @PostMapping
    public String submit(@Valid UserResource resource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.create(resource);
        return "redirect:/register?success";
    }

    @ModelAttribute("countries")
    public Map<String, String> countries() {
        return countryService.getAll();
    }
}
