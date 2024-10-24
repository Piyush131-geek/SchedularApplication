package com.aubank.apischeduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aubank.apischeduler.model.ApiConfig;

@Repository
public interface ApiConfigRepository extends JpaRepository<ApiConfig, Long> {
    
}

