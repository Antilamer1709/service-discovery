package com.antilamer.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/gateway")
public class GatewayController {

    @GetMapping("/{userId}")
    public String getMessage(@PathVariable long userId) {
        return "gateway";
    }

}
