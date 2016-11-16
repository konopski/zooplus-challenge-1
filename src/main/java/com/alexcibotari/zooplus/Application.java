package com.alexcibotari.zooplus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);

        ApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        String serverPort = env.getProperty("server.port", "8080");
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                serverPort,
                InetAddress.getLocalHost().getHostAddress(),
                serverPort);
    }

}
