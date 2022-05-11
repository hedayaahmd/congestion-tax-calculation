package com.engine.congestiontaxcalculator.repository;

import com.engine.congestiontaxcalculator.entity.City;
import com.engine.congestiontaxcalculator.entity.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Long> {

    List<Holiday> findByCity(City city);
}
