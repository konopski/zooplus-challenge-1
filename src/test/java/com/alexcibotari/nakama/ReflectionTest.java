package com.alexcibotari.nakama;

import com.alexcibotari.zooplus.utils.validation.Validator;
import com.alexcibotari.zooplus.web.rest.resource.UserResource;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@SpringBootTest
public class ReflectionTest {


    @Test
    public void getAnnotations() {
        System.out.println(Validator.extractConstraint(UserResource.class));
    }

}
