package com.aubank.apischeduler.services;

import org.springframework.http.ResponseEntity;

import com.aubank.apischeduler.model.ApiRequest;

public interface ApiServiceInterface {
    ResponseEntity<String> handleGetRequest(ApiRequest apiRequest);
    ResponseEntity<String> handlePostRequest(ApiRequest apiRequest);
    ResponseEntity<String> handlePutRequest(ApiRequest apiRequest);
    ResponseEntity<String> handleDeleteRequest(ApiRequest apiRequest);
}
