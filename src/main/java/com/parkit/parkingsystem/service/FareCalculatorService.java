package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is correct
		float durationMillis = outHour - inHour;
		float durationHour = durationMillis / 1000 / 60 / 60; // Convert from milliseconds to hours
		System.out.println("duration: " + durationHour);

		if (durationHour <= 0.5) {  // Free half hour
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(0);
				break;
			}
			case BIKE: {
				ticket.setPrice(0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		} else {  //
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(durationHour * Fare.CAR_RATE_PER_HOUR);
				break;
			}
			case BIKE: {
				ticket.setPrice(durationHour * Fare.BIKE_RATE_PER_HOUR);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}
}
