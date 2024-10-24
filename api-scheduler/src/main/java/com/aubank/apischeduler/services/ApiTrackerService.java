package com.aubank.apischeduler.services;

import com.aubank.apischeduler.model.ApiTracker;
import com.aubank.apischeduler.repository.ApiTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiTrackerService {

    @Autowired
    private ApiTrackerRepository apiTrackerRepository;

    // Fetch all API hits
    public List<ApiTracker> getAllApiHits() {
        return apiTrackerRepository.findAll();
    }

    // Fetch API hits by HTTP method
    public List<ApiTracker> getApiHitsByMethod(String method) {
        return apiTrackerRepository.findByMethod(method);
    }
}
