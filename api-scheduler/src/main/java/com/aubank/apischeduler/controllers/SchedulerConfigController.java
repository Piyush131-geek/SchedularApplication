package com.aubank.apischeduler.controllers;

import com.aubank.apischeduler.model.ApiConfig;
import com.aubank.apischeduler.services.ApiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class SchedulerConfigController {

    @Autowired
    private ApiConfigService apiConfigService;

    // API to add a new configuration
    @PostMapping("/add")
    public ResponseEntity<String> addApiConfig(@RequestBody ApiConfig apiConfig) {
        apiConfigService.addApiConfig(apiConfig);
        return ResponseEntity.ok("API configuration added successfully.");
    }

    // API to update an existing configuration
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateApiConfig(@PathVariable Long id, @RequestBody ApiConfig updatedConfig) {
        ApiConfig updated = apiConfigService.updateApiConfig(id, updatedConfig);
        if (updated != null) {
            return ResponseEntity.ok("API configuration updated successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid API config ID.");
    }

    // API to retrieve all configurations
    @GetMapping("/list")
    public ResponseEntity<Iterable<ApiConfig>> listApiConfigs() {
        return ResponseEntity.ok(apiConfigService.listApiConfigs());
    }
}
