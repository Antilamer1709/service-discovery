package com.antilamer.client.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/client1")
public class ClientController {

    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        return "client1; userId " + userId;
    }


    @PostMapping("files/upload")
    public void upload(@RequestParam("files") MultipartFile[] files) {
        System.out.println(files);
    }

}
