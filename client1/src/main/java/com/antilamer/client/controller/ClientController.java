package com.antilamer.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client1")
public class ClientController {

    @GetMapping("/{userId}")
    public String getClient(@PathVariable long userId) {
        return "client1; userId " + userId;

    }

}
