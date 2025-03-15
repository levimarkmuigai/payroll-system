package com.example.payroll.repositories;

import com.example.payroll.model.TaxBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxBandRepository extends JpaRepository<TaxBand, Long>{

}
