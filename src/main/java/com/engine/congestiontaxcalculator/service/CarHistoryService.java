package com.engine.congestiontaxcalculator.service;

import com.engine.congestiontaxcalculator.entity.History;
import com.engine.congestiontaxcalculator.exception.TaxException;

import java.util.Date;
import java.util.List;

public interface CarHistoryService {

    History saveCarHistory(String plateNumber, String cityCode, Date date) throws TaxException;

    List<History> carHistory(String plateNumber, String cityCode) throws TaxException;
}
