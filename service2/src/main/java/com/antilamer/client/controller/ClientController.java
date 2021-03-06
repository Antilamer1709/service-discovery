package com.antilamer.client.controller;

import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("v1/service2")
public class ClientController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;


    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        log.info("service2 userId: " + userId);
        String url = discoveryClient.getNextServerFromEureka("service1", false).getHomePageUrl();
        String firstClientResponse = restTemplate.getForEntity(url + "/v1/service1/" + userId, String.class).getBody();

        return firstClientResponse + "_____      Service2;    Port: " + port + ";     UserId: " + userId;
    }


    @PostMapping("files/upload")
    public String upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        String url = discoveryClient.getNextServerFromEureka("service1", false).getHomePageUrl();
        String serverUrl = url + "/v1/service1/files/upload";

        HttpEntity<MultiValueMap<String, Object>> requestEntity = createRequestEntity(files);

        return restTemplate.postForEntity(serverUrl, requestEntity, String.class).getBody();
    }

    private HttpEntity<MultiValueMap<String, Object>> createRequestEntity(MultipartFile[] files) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            body.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        }

        return new HttpEntity<>(body, headers);
    }


    /*
        Guys from spring recommend to use that class
        https://github.com/spring-projects/spring-framework/issues/18147#issuecomment-453431470
     */
    private class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }
        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }

}
