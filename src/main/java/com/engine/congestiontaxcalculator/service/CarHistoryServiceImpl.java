package com.engine.congestiontaxcalculator.service;

import com.engine.congestiontaxcalculator.entity.Car;
import com.engine.congestiontaxcalculator.entity.City;
import com.engine.congestiontaxcalculator.entity.History;
import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.repository.CarRepository;
import com.engine.congestiontaxcalculator.repository.CityRepository;
import com.engine.congestiontaxcalculator.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CarHistoryServiceImpl implements CarHistoryService{

    private static final Logger LOGGER = Logger.getLogger(CarHistoryServiceImpl.class.getSimpleName());

    @Autowired
    CarRepository carRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public History saveCarHistory(String plateNumber, String cityCode, Date date) throws TaxException {
        Car car = carRepository.findOneByNumber(plateNumber);
        City city = cityRepository.findOneByCode(cityCode);
        if(city == null) {
            LOGGER.log(Level.WARNING, "Invalid City");
            throw new TaxException("wrong city code");
        }
        History history = new History();
        history.setCar(car);
        history.setCity(city);
        history.setDate(date);
        return historyRepository.save(history);
    }

    @Override
    public List<History> carHistory(String plateNumber, String cityCode) throws TaxException {
        Car car = carRepository.findOneByNumber(plateNumber);
        if(car == null) {
            LOGGER.log(Level.WARNING, "car not registered");
            throw new TaxException("car not registered");
        }        City city = cityRepository.findOneByCode(cityCode);
        return historyRepository.findByCar(car).stream().filter(history -> history.getCity() == city).collect(Collectors.toList());
    }

}
