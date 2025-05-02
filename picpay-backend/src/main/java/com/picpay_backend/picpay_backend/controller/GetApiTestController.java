package com.picpay_backend.picpay_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/test")
public class GetApiTestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String testRestTemplate() {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        return restTemplate.getForEntity(url, String.class).getBody();
    }


}
