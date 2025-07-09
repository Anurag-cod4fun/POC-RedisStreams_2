package com.test.rstreamtest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.rstreamtest.dto.EventEnvelope;

//@Service
//public class RedisServiceEventPublisher {
//	private static final String STREAM_KEY = "order-stream";
//	
//	@Autowired
//	private RedisTemplate<String, EventEnvelope> redisTemplate;
//	
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	public void sendEvent(EventEnvelope envelope) {
//		try {
//			String json = objectMapper.writeValueAsString(envelope);
//			redisTemplate.opsForStream().add(MapRecord.create(STREAM_KEY, Map.of("event", json)));
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException("Failed to serialize event envelope", e);
//		}
//	}
//}

@Service
public class RedisServiceEventPublisher {

    private static final String STREAM_KEY = "order-stream";

    @Autowired
    private RedisTemplate<String, EventEnvelope> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendEvent(EventEnvelope envelope) {
        try {
            String json = objectMapper.writeValueAsString(envelope);
            redisTemplate.opsForStream().add(MapRecord.create(STREAM_KEY, Map.of("event", json)));
            System.out.println("Event published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event envelope", e);
        }
    }
}
