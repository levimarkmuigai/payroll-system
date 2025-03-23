package com.example.payroll.repositories;

import com.example.payroll.model.LeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {
    
}
