package com.engine.congestiontaxcalculator.controller;

import com.engine.congestiontaxcalculator.entity.Holiday;
import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("holidays")
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @GetMapping("/{cityCode}")
    public List<Holiday> getCityHolidays(@PathVariable(value = "cityCode") String cityCode) {
        try {
            return holidayService.getHolidays(cityCode);
        } catch (TaxException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
