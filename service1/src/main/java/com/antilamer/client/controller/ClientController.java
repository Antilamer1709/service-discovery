package com.antilamer.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("v1/service1")
public class ClientController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) throws InterruptedException {
        log.info("service1 userId: " + userId);
        if (port.equals("8082")) {
            throw new RuntimeException("Error");
        }
        return "Service1;    Port: " + port + ";     UserId: " + userId;
    }


    @PostMapping("files/upload")
    public void upload(@RequestParam("files") MultipartFile[] files) throws InterruptedException {
        if (port.equals("8082")) {
            Thread.sleep(7000);
        }
        String path = "D:\\fileStorage";

        for (MultipartFile file : files) {
            try(OutputStream out = new FileOutputStream(new File(path + File.separator + file.getOriginalFilename()))) {
                out.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
