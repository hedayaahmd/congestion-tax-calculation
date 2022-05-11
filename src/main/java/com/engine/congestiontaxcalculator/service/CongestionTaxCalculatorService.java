package com.engine.congestiontaxcalculator.service;

import com.engine.congestiontaxcalculator.entity.*;
import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CongestionTaxCalculatorService {

    private static final Logger LOGGER = Logger.getLogger(CongestionTaxCalculatorService.class.getSimpleName());

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    TaxRateRepository taxRateRepository;

    @Value("${vehicles}")
    private List<String> taxExempt;

    @Value("${singleCharge}")
    private Double singleChargeTime;

    @Value("${daysBeforePublicHoliday}")
    private Integer daysBeforePublicHoliday;

    public Double getTax(String vehicleNumber, String cityCode) throws TaxException {
        Car car = carRepository.findOneByNumber(vehicleNumber);
        if (car == null) {
            LOGGER.log(Level.WARNING, "Invalid car number");
            throw new TaxException("Invalid Car number");
        }
        List<History> carHistory = historyRepository.findByCar(car);
        List<Date> dates = carHistory.stream().map(history -> history.getDate()).collect(Collectors.toList());
        return getTax(car.getType(), (Date[]) dates.toArray(), cityCode);

    }

    public Double getTax(String vehicle, Date[] dates, String cityCode) throws TaxException {
        Double totalFee = 0d;
        if (dates.length > 0) {
            City city = cityRepository.findOneByCode(cityCode);

            if (city == null) {
               LOGGER.log(Level.WARNING, "Invalid City");
               throw new TaxException("Invalid City");
            }
            for (int i = 0; i < dates.length; i++) {
                Double tollFee, previousTollFee = 0d;
                tollFee = getTollFee(dates[i], vehicle, city);
                if (i != 0 && dateDiff(dates[i - 1], dates[i])) {
                    previousTollFee = getTollFee(dates[i - 1], vehicle, city);
                    totalFee -= previousTollFee;
                    tollFee = Math.max(previousTollFee, tollFee);
                }
                totalFee += tollFee;
            }
            if (totalFee > singleChargeTime) {
                totalFee = singleChargeTime;
            }
        }
        return totalFee;
    }

    private boolean dateDiff(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MICROSECONDS.toMinutes(diff) > singleChargeTime;
    }

    private boolean isTollFreeVehicle(String vehicleType) {
        if (vehicleType == null) {
            return false;
        }
        return taxExempt.contains(vehicleType);
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Boolean isTollFreeDate(Date date, City city) {
        LocalDateTime localDate = convertToLocalDateTimeViaInstant(date);

        int year = localDate.getYear();

        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            return true;
        }

        List<Holiday> holidayList = holidayRepository.findByCity(city);

        // validate for year 2013
        if (year == 2013) {
            for (Holiday holiday : holidayList) {

                LocalDateTime startDate = convertToLocalDateTimeViaInstant(holiday.getDate());
                LocalDateTime beforeDate = startDate.minusDays(this.daysBeforePublicHoliday);

                // checking public holidays, a day before a public holiday
                if((!localDate.isBefore(beforeDate)) && localDate.isBefore(startDate)) {
                    return true;
                }
            }
        } else if (localDate.getMonth() == Month.JULY) {
            return true;
        }
        return false;
    }


    public Double getTollFee(Date date, String vehicle, City city) {
        if (isTollFreeDate(date, city) || isTollFreeVehicle(vehicle)) {
            return 0d;
        }
        List<TaxRate> taxRates = taxRateRepository.findByCity(city);
        double taxRate = 0.0d;
        LocalDateTime localDate = convertToLocalDateTimeViaInstant(date);

        for (TaxRate rate : taxRates) {

            LocalDateTime startDate = convertToLocalDateTimeViaInstant(rate.getStartDate());
            LocalDateTime endDate = convertToLocalDateTimeViaInstant(rate.getEndDate());

            LocalDateTime localStartDate = localDate.withHour(startDate.getHour()).withMinute(startDate.getMinute());
            LocalDateTime localEndDate = localDate.withHour(endDate.getHour()).withMinute(endDate.getMinute());
            Boolean isBetween = (!localDate.isBefore(localStartDate)) && (localDate.isBefore(localEndDate));
            if (isBetween) {
                taxRate = rate.getTax();
            }
        }
        return taxRate;
    }


}
