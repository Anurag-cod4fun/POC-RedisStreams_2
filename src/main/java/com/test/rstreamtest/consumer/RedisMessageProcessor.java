package com.test.rstreamtest.consumer;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.rstreamtest.dto.EventEnvelope;

import lombok.RequiredArgsConstructor;

//@Service
//@RequiredArgsConstructor
//public class RedisMessageProcessor implements StreamListener<String, MapRecord<String,String,String>> {
//    private final ObjectMapper mapper = new ObjectMapper();
//    
//
//	@Override
//	public void onMessage(MapRecord<String,String,String> message) {
//		// TODO Auto-generated method stub
//		System.out.println(message.getValue());
//	}
//}

@Service
public class RedisMessageProcessor implements StreamListener<String, MapRecord<String, String, String>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        try {
            String eventJson = message.getValue().get("event");
            EventEnvelope event = mapper.readValue(eventJson, EventEnvelope.class);
            System.out.println("Received Event: " + event.getEventType() + " | User: " + event.getUser().getName());
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}