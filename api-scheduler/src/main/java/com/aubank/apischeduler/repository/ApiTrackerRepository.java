package com.aubank.apischeduler.repository;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aubank.apischeduler.model.ApiTracker;

@Repository
public interface ApiTrackerRepository extends JpaRepository<ApiTracker, Long> {
	List<ApiTracker> findByMethod(String method);

    // Fetch API hits by status (e.g., Success or Failed)
    List<ApiTracker> findByStatus(String status);

    // Fetch API hits within a specific time range
    List<ApiTracker> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
