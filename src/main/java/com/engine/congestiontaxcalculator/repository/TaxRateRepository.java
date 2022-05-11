package com.engine.congestiontaxcalculator.repository;

import com.engine.congestiontaxcalculator.entity.City;
import com.engine.congestiontaxcalculator.entity.TaxRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRateRepository extends CrudRepository<TaxRate, Long> {

    List<TaxRate> findByCity(City city);
}
