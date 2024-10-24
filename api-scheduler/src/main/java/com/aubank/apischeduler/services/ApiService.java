package com.aubank.apischeduler.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;  // Make sure to import this
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aubank.apischeduler.model.ApiConfig;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    // Execute the API request based on ApiConfig
    public void executeApi(ApiConfig apiConfig) {
        try {
            // Prepare the headers from the serialized string
            HttpHeaders headers = new HttpHeaders();
            headers.setAll(deserializeHeaders(apiConfig.getHeaders()));

            // Create the request entity with headers and body
            HttpEntity<String> entity = new HttpEntity<>(apiConfig.getRequestBody(), headers);

            // Convert the string method (e.g., "POST", "GET") to the HttpMethod enum
            HttpMethod method = HttpMethod.valueOf(apiConfig.getMethod().toUpperCase());
            if (method == null) {
                throw new IllegalArgumentException("Invalid HTTP method: " + apiConfig.getMethod());
            }

            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(
                    apiConfig.getUrl(), method, entity, String.class);

            // Use getStatusCode().value() to get the status code as an integer
            logExecution(apiConfig, response.getStatusCode().value(), true);

        } catch (Exception e) {
            logExecution(apiConfig, 500, false);  // Log error status
        }
    }

    // Deserialize headers
    private Map<String, String> deserializeHeaders(String headersJson) {
        // Deserialize headers from JSON to a Map
        return new HashMap<>();  // Implement actual logic
    }

    // Log the execution (success or failure)
    private void logExecution(ApiConfig apiConfig, int statusCode, boolean success) {
        // Log the API execution details (implement logging/auditing here)
        System.out.println("Executed API: " + apiConfig.getUrl() + " with status: " + statusCode);
    }
}

