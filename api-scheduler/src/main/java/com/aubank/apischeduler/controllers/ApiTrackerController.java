package com.aubank.apischeduler.controllers;

import com.aubank.apischeduler.model.ApiTracker;
import com.aubank.apischeduler.services.ApiTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class ApiTrackerController {

    @Autowired
    private ApiTrackerService apiTrackerService;

    // Endpoint to retrieve all API hit records
    @GetMapping("/all")
    public ResponseEntity<List<ApiTracker>> getAllApiHits() {
        List<ApiTracker> apiHits = apiTrackerService.getAllApiHits();
        return ResponseEntity.ok(apiHits);
    }

    // Endpoint to retrieve API hits filtered by method
    @GetMapping("/by-method")
    public ResponseEntity<List<ApiTracker>> getApiHitsByMethod(@RequestParam String method) {
        List<ApiTracker> apiHits = apiTrackerService.getApiHitsByMethod(method);
        return ResponseEntity.ok(apiHits);
    }

    // Optional: Add more filtering options such as status or timestamp if needed
}
