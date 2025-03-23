package com.example.payroll.repositories;

import com.example.payroll.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {

}
