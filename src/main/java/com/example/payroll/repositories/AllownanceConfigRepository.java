package com.example.payroll.repositories;

import com.example.payroll.model.AllowanceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllownanceConfigRepository extends  JpaRepository<AllowanceConfig, Long>{

}
