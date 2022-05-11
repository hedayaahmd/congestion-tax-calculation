package com.engine.congestiontaxcalculator.model.vehicle;

public class Emergency implements Vehicle {
	@Override
	public String getVehicleType() {
		return "Emergency";
	}
}
