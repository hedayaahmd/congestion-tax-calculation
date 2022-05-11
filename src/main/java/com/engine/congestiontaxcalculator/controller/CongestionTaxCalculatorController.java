package com.engine.congestiontaxcalculator.controller;

import com.engine.congestiontaxcalculator.model.request.TaxCalculatorRequest;
import com.engine.congestiontaxcalculator.model.response.TaxCalculatorResponse;
import com.engine.congestiontaxcalculator.service.CongestionTaxCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("congestionTax")
public class CongestionTaxCalculatorController {

    @Autowired
    CongestionTaxCalculatorService congestionTaxCalculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<TaxCalculatorResponse> calculateTax(@RequestBody TaxCalculatorRequest taxCalculatorRequest) {
        TaxCalculatorResponse response;
        try {
            Double tax = congestionTaxCalculatorService.getTax(taxCalculatorRequest.getVehicle(), taxCalculatorRequest.getDates(), taxCalculatorRequest.getCityCode());
            response = new TaxCalculatorResponse(null, tax, "Tax calculated successfully, Vehicle :" + taxCalculatorRequest.getVehicle() + " Amount : " + tax);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = new TaxCalculatorResponse(ex.getLocalizedMessage(), 0d, "Tax Calculation Failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("carNumber/calculate")
    public ResponseEntity<TaxCalculatorResponse> calculateCarTax(@RequestBody TaxCalculatorRequest taxCalculatorRequest) {
        TaxCalculatorResponse response;
        try {
            Double tax = congestionTaxCalculatorService.getTax(taxCalculatorRequest.getVehicle(), taxCalculatorRequest.getCityCode());
            response = new TaxCalculatorResponse(null, tax, "Tax calculated successfully, Vehicle :" + taxCalculatorRequest.getVehicle() + " Amount : " + tax);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = new TaxCalculatorResponse(ex.getLocalizedMessage(), 0d, "Tax Calculation Failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
