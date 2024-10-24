package com.aubank.apischeduler.repository;

import com.aubank.apischeduler.model.ApiExecutionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiExecutionAuditRepository extends JpaRepository<ApiExecutionAudit, Long> {
}
