package com.example.payroll.repositories;

import com.example.payroll.model.NSSFConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NSSFConfigRepository extends JpaRepository<NSSFConfig, Long> {
}
