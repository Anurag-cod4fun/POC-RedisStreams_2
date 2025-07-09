package com.test.rstreamtest.dto;


public class EventEnvelope  {
	private String eventType;
	private User user;
	
	public EventEnvelope() {
        // Required for Jackson
    }


    public EventEnvelope(String eventType, User user) {
        this.eventType = eventType;
        this.user = user;
    }
    
    public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
