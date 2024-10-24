package com.aubank.apischeduler.services;

import com.aubank.apischeduler.model.ApiConfig;
import com.aubank.apischeduler.repository.ApiConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiConfigService {

    @Autowired
    private ApiConfigRepository apiConfigRepository;

    @Autowired
    private SchedulerService schedulerService;  // Inject SchedulerService to trigger scheduling

    // Add a new API configuration and immediately schedule it
    public ApiConfig addApiConfig(ApiConfig apiConfig) {
        ApiConfig savedConfig = apiConfigRepository.save(apiConfig);
        
        // Immediately schedule the API after adding
        schedulerService.scheduleApiImmediately(savedConfig);
        
        return savedConfig;
    }

    // Update existing API configuration
    public ApiConfig updateApiConfig(Long id, ApiConfig updatedConfig) {
        Optional<ApiConfig> existingConfigOptional = apiConfigRepository.findById(id);
        
        if (existingConfigOptional.isPresent()) {
            ApiConfig existingConfig = existingConfigOptional.get();
            existingConfig.setUrl(updatedConfig.getUrl());
            existingConfig.setMethod(updatedConfig.getMethod());
            existingConfig.setHeaders(updatedConfig.getHeaders());
            existingConfig.setRequestBody(updatedConfig.getRequestBody());
            existingConfig.setSchedule(updatedConfig.getSchedule());

            return apiConfigRepository.save(existingConfig);
        }
        return null;
    }

    public Iterable<ApiConfig> listApiConfigs() {
        return apiConfigRepository.findAll();
    }

    public Optional<ApiConfig> getApiConfigById(Long id) {
        return apiConfigRepository.findById(id);
    }
}
