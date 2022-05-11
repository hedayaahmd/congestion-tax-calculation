package com.engine.congestiontaxcalculator.repository;

import com.engine.congestiontaxcalculator.entity.Car;
import com.engine.congestiontaxcalculator.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {

    List<History> findByCar(Car car);
}
