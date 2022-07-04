package com.cjava.hystrix.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api/v1")
public class PruebaController {

	@HystrixCommand(fallbackMethod = "circuitBreaker",
            commandKey = "health", groupKey = "health")
    @GetMapping("/health")
    public String hello()
	{
        //Que se caiga
        if (RandomUtils.nextBoolean()) {
            throw new RuntimeException("health fail");
        }
        return "ok";
    }

    @HystrixCommand(fallbackMethod = "circuitBreaker" ,commandKey = "holamundo" ,groupKey = "holamundo")
    @GetMapping("/holamundo")
    public String holaMundo() {
        //Wrong
        if (RandomUtils.nextBoolean()) {
            throw new RuntimeException("holamundo fail");
        }
        return "Circuit Breaker";
    }

    public String circuitBreaker() {
        return "Circuit Breaker in Action...";
    }
}
