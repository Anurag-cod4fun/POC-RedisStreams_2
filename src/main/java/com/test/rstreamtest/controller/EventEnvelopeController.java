package com.test.rstreamtest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.rstreamtest.dto.EventEnvelope;
import com.test.rstreamtest.dto.User;
import com.test.rstreamtest.service.RedisServiceEventPublisher;


@RestController
@RequestMapping("/events")
public class EventEnvelopeController {

    @Autowired
    private RedisServiceEventPublisher eventPublisher;
    

    @PostMapping("/publish")
    public String publishEvent(@RequestBody EventEnvelope event) {
        eventPublisher.sendEvent(event);
        return "Event published to Redis stream!";
    }

    @GetMapping("/sample")
    public String publishSample() {
        EventEnvelope event = new EventEnvelope("USER_REGISTERED", new User("abc123", "Alice"));
        eventPublisher.sendEvent(event);
        
        return "Sample event published.";
    }
    
}