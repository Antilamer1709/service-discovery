package com.antilamer.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("v1/service2")
public class ClientController {

    @Value("${server.port}")
    private String port;

    @Autowired
    RestTemplate restTemplate;


    /*
        In this example we use Ribbon directly by @LoadBalanced annotation. Therefore we don't need to have @EnableEurekaClient.
        We can use service name directly in the restTemplate
     */
    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        String firstClientResponse = restTemplate.exchange("http://service1/v1/service1/{userId}", // service name is used
                HttpMethod.GET,
                null,
                String.class,
                userId)
                .getBody();

        return firstClientResponse + "_____      Service2;    Port: " + port + ";     UserId: " + userId;
    }


}
