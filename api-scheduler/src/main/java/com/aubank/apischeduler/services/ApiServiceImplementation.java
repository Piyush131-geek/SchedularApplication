package com.aubank.apischeduler.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aubank.apischeduler.model.ApiConfig;
import com.aubank.apischeduler.model.ApiRequest;


@Service
public class ApiServiceImplementation implements ApiServiceInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> handleGetRequest(ApiRequest apiRequest) {
        return restTemplate.getForEntity(apiRequest.getUrl(), String.class);
    }

    @Override
    public ResponseEntity<String> handlePostRequest(ApiRequest apiRequest) {
        HttpHeaders headers = createHeaders(apiRequest);
        HttpEntity<String> entity = new HttpEntity<>(apiRequest.getBody(), headers);
        
        if (apiRequest.getUrl().contains("node-api")) {
        	
            return callNodePostApi(entity, apiRequest.getUrl());
        } else if (apiRequest.getUrl().contains("python-api")) {
            return callPythonPostApi(entity, apiRequest.getUrl());
        } else {
            return ResponseEntity.badRequest().body("Invalid API endpoint.");
        }
    }

    @Override
    public ResponseEntity<String> handlePutRequest(ApiRequest apiRequest) {
        HttpHeaders headers = createHeaders(apiRequest);
        HttpEntity<String> entity = new HttpEntity<>(apiRequest.getBody(), headers);
        restTemplate.put(apiRequest.getUrl(), entity);
        return ResponseEntity.ok("PUT request successful");
    }

    @Override
    public ResponseEntity<String> handleDeleteRequest(ApiRequest apiRequest) {
        restTemplate.delete(apiRequest.getUrl());
        return ResponseEntity.ok("DELETE request successful");
    }

    private ResponseEntity<String> callNodePostApi(HttpEntity<String> entity, String url) {
        return restTemplate.postForEntity(url, entity, String.class);
    }

    private ResponseEntity<String> callPythonPostApi(HttpEntity<String> entity, String url) {
        return restTemplate.postForEntity(url, entity, String.class);
    }

    private HttpHeaders createHeaders(ApiRequest apiRequest) {
        HttpHeaders headers = new HttpHeaders();
        if (apiRequest.getHeaders() != null) {
            apiRequest.getHeaders().forEach(headers::add);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    public ResponseEntity<String> executeApiFromConfig(ApiConfig apiConfig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(deserializeHeaders(apiConfig.getHeaders()));

        HttpEntity<String> entity = new HttpEntity<>(apiConfig.getRequestBody(), headers);
        System.out.println("Sending request to: " + apiConfig.getUrl());

        // Mocked response for testing (replace with actual API call logic)
        return ResponseEntity.ok("API call to " + apiConfig.getUrl() + " executed successfully");
    }

    // Deserialize the headers from JSON/String format
    private Map<String, String> deserializeHeaders(String headersJson) {
        // Convert the headers string back to a map (you can use Jackson or Gson for this)
        return new HashMap<>(); // Placeholder, implement your deserialization logic here
    }
}
