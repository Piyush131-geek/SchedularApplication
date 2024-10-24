package com.aubank.apischeduler.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApiExecutionAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long apiConfigId;                 // Link to ApiConfig
    private LocalDateTime executionTime;
    private int statusCode;
    private boolean success;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApiConfigId() {
		return apiConfigId;
	}
	public void setApiConfigId(Long apiConfigId) {
		this.apiConfigId = apiConfigId;
	}
	public LocalDateTime getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(LocalDateTime executionTime) {
		this.executionTime = executionTime;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

    
}
