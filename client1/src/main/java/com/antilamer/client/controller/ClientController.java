package com.antilamer.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("v1/service1")
public class ClientController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        return "Service1;    Port: " + port + ";     UserId: " + userId;
    }


    @PostMapping("files/upload")
    public void upload(@RequestParam("files") MultipartFile[] files) {
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
