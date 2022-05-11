package com.engine.congestiontaxcalculator.service;

import com.engine.congestiontaxcalculator.entity.City;
import com.engine.congestiontaxcalculator.entity.Holiday;
import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.repository.CityRepository;
import com.engine.congestiontaxcalculator.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService{
    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public List<Holiday> getHolidays(String cityCode) throws TaxException {
        City city = cityRepository.findOneByCode(cityCode);
        if (city == null) {
            throw new TaxException("invalid city code");
        }
        return holidayRepository.findByCity(city);
    }
}
