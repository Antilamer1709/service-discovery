package com.antilamer.client.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/client2")
public class ClientController {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private EurekaClient discoveryClient;

    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        String url = discoveryClient.getNextServerFromEureka("client1", false).getHomePageUrl();
        String firstClientResponse = restTemplate().getForEntity(url + "/api/client1/" + userId, String.class).getBody();

        return firstClientResponse + " client2; userId " + userId;

    }

}
