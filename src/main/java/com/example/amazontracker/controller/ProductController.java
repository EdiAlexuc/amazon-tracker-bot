package com.example.amazontracker.controller;

import com.example.amazontracker.restservice.service.ProductService;
import com.twilio.twiml.fax.Receive;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/track")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void getMessage(@RequestParam("From") String From, @RequestParam("To") String To, @RequestParam("Body") String Body){
        service.trackAndSendReply(From, To, Body);
    }
}
