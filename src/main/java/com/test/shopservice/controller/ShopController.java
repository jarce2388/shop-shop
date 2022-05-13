package com.test.shopservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class ShopController {

    @GetMapping
    public String getWelcome(){
        return "*** Bienvenido a Shop-Shop ***";
    }
}
