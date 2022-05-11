package com.engine.congestiontaxcalculator.model.request;

import com.engine.congestiontaxcalculator.model.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TaxCalculatorRequest {

    private String vehicle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Amsterdam")
    private Date[] dates;

    private String cityCode;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
