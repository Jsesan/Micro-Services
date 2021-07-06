package com.kinandcarta.microservices.limitsservices.controller;

import com.kinandcarta.microservices.limitsservices.bean.Limits;
import com.kinandcarta.microservices.limitsservices.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits retrieveLimits(){
        return new Limits(configuration.getMin(),
                                    configuration.getMax());
    }
}
