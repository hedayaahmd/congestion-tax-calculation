package com.engine.congestiontaxcalculator;

import com.engine.congestiontaxcalculator.exception.TaxException;
import com.engine.congestiontaxcalculator.model.vehicle.Vehicle;
import com.engine.congestiontaxcalculator.model.vehicle.VehicleFactory;
import com.engine.congestiontaxcalculator.service.CongestionTaxCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CongestiontaxcalculatorApplicationTests {

	@Autowired
	@InjectMocks
	private CongestionTaxCalculatorService taxService;

	@Test
	public void testCarTax() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 15:31:59"), getDateFromString("25-11-2013 16:10:59") };
		assertEquals(Double.valueOf(18), taxService.getTax("Car", dates, "GSE"));
	}

	@Test
	public void testSingleChargeRule() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 09:08:59") };
		assertEquals(Double.valueOf(13), taxService.getTax("Car", dates, "GSE"));
	}

	@Test
	public void testNoneSingleChargeRule() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 10:20:59") };
		assertEquals(Double.valueOf(21), taxService.getTax("Car", dates, "GSE"));
	}

	@Test
	public void testHoliday() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("28-03-2013 15:31:59"), getDateFromString("28-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax("CAR", dates, "GSE"));
	}

	@Test
	public void testHolidayWithBeforeDay() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("27-03-2013 15:31:59"), getDateFromString("27-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax("Car", dates, "GSE"));
	}

	@Test
	public void testExceptVehicles() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Military");
		Date[] dates = { getDateFromString("27-02-2013 15:31:59"), getDateFromString("27-02-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax("Car", dates, "GSE"));
	}

	@Test
	public void testMaximumAmount() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("12-02-2013 06:10:59"), getDateFromString("12-02-2013 06:30:59"),
				getDateFromString("12-02-2013 07:05:59"), getDateFromString("12-02-2013 07:30:59"),
				getDateFromString("12-02-2013 08:20:59"), getDateFromString("12-02-2013 08:40:59"),
				getDateFromString("12-02-2013 09:30:59"), getDateFromString("12-02-2013 15:10:59"),
				getDateFromString("12-02-2013 15:40:59"), getDateFromString("12-02-2013 16:30:59"),
				getDateFromString("12-02-2013 17:30:59"), getDateFromString("12-02-2013 18:10:59") };
		assertEquals(Double.valueOf(60), taxService.getTax("Car", dates, "GSE"));
	}

	public Date getDateFromString(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		String dateInString = dateStr;
		Date date;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

}
