package com.aubank.apischeduler.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String method;
    private String headers;
    private String requestBody;
    private String schedule;              // Cron expression or fixed delay
    private LocalDateTime lastExecutionTime;
    private LocalDateTime nextExecutionTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public LocalDateTime getLastExecutionTime() {
		return lastExecutionTime;
	}
	public void setLastExecutionTime(LocalDateTime lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}
	public LocalDateTime getNextExecutionTime() {
		return nextExecutionTime;
	}
	public void setNextExecutionTime(LocalDateTime nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}

    
}
