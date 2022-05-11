package com.engine.congestiontaxcalculator.repository;

import com.engine.congestiontaxcalculator.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    Car findOneByNumber(String plateNumber);
}
