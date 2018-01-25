package com.bh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bh.service.ServiceClient;

@RestController
public class ConsumerController {

    @Autowired
    ServiceClient serviceClient;

    @RequestMapping(value = "/instance-info", method = RequestMethod.GET)
    public String add() {
        return serviceClient.getInstanceInfo();
    }

}