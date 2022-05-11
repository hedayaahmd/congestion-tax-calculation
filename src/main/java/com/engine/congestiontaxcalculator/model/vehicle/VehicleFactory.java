package com.engine.congestiontaxcalculator.model.vehicle;

public class VehicleFactory {
	
	public Vehicle getVehicle(String vehicleType)
    {
        if (vehicleType == null || vehicleType.isEmpty())
            return null;
        if (VehicleType.CAR.getType().equalsIgnoreCase(vehicleType)) {
            return new Car();
        }
        else if (VehicleType.MOTORBIKE.getType().equals(vehicleType)) {
            return new Motorbike();
        }
        else if (VehicleType.TRACTOR.getType().equals(vehicleType)) {
            return new Tractor();
        }
        else if (VehicleType.EMERGENCY.getType().equals(vehicleType)) {
            return new Emergency();
        }
        else if (VehicleType.DIPLOMAT.getType().equals(vehicleType)) {
            return new Diplomat();
        }
        else if (VehicleType.FOREIGN.getType().equals(vehicleType)) {
            return new Foreign();
        }
        else if (VehicleType.MILITARY.getType().equals(vehicleType)) {
            return new Military();
        }
        return null;
    }

}
