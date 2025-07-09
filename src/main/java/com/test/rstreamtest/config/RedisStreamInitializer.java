package com.test.rstreamtest.config;

import java.util.Map;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Component;

import com.test.rstreamtest.dto.EventEnvelope;

import jakarta.annotation.PostConstruct;

@Component
public class RedisStreamInitializer {

    public static final String STREAM_KEY = "order-stream";
    public static final String GROUP_NAME = "mygroup";

    private RedisConnectionFactory connectionFactory;
    private RedisTemplate<String, EventEnvelope> redisTemplate;
    
    public RedisStreamInitializer(RedisConnectionFactory connectionFactory, RedisTemplate<String, EventEnvelope> redisTemplate) {
    	this.connectionFactory = connectionFactory;
    	this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void createStreamAndGroup() {
        try {
            StreamOperations<String, ?, ?> streamOps = redisTemplate.opsForStream();

            // Create stream if it doesn't exist
            if (!connectionFactory.getConnection().keyCommands().exists(STREAM_KEY.getBytes())) {
            	streamOps.add(MapRecord.create(STREAM_KEY, Map.of("init", "init")));
                System.out.println("Stream created.");
            }

            // Try to create group
            try {
                connectionFactory.getConnection().streamCommands()
                        .xGroupCreate(STREAM_KEY.getBytes(), GROUP_NAME, ReadOffset.from("0"), true);
                System.out.println("Group created.");
            } catch (Exception e) {
                System.out.println("Group might already exist: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Stream init error: " + e.getMessage());
        }
    }
}