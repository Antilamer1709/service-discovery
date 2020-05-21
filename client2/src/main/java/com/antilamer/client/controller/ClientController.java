package com.antilamer.client.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/client2")
public class ClientController {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;


    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        String url = discoveryClient.getNextServerFromEureka("client1", false).getHomePageUrl();
        String firstClientResponse = restTemplate().getForEntity(url + "/api/client1/" + userId, String.class).getBody();

        return firstClientResponse + " client2; userId " + userId;
    }


    @PostMapping("files/upload")
    public void upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(files);
        String url = discoveryClient.getNextServerFromEureka("client1", false).getHomePageUrl();
        String response = restTemplate().postForEntity(url + "/api/client1/files/upload", requestEntity, String.class).getBody();
        System.out.println(response);
    }

    private HttpEntity<MultiValueMap<String, Object>> createRequestEntity(MultipartFile[] files) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            body.add(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
        }

        return new HttpEntity<>(body, headers);
    }

}
