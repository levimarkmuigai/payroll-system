package com.example.payroll.repositories;

import com.example.payroll.model.NHIFConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NHIFConfigRepository extends JpaRepository<NHIFConfig, Long>{

}
