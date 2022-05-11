package com.engine.congestiontaxcalculator.service;

import com.engine.congestiontaxcalculator.entity.Holiday;
import com.engine.congestiontaxcalculator.exception.TaxException;

import java.util.List;

public interface HolidayService {

    List<Holiday> getHolidays(String cityCode) throws TaxException;
}
