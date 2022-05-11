package com.engine.congestiontaxcalculator.repository;

import com.engine.congestiontaxcalculator.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    City findOneByCode(String code);
}
