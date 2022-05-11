package com.engine.congestiontaxcalculator.controller;

import com.engine.congestiontaxcalculator.entity.History;
import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.service.CarHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("history")
public class CarHistoryController {

    @Autowired
    CarHistoryService carHistoryService;

    @GetMapping("/car/{plateNumber}/{cityCode}")
    public List<History> getCarHistory(@PathVariable(value = "plateNumber") String plateNumber, @PathVariable(value = "cityCode") String cityCode) {
        try {
            return carHistoryService.carHistory(plateNumber, cityCode);
        } catch (TaxException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
