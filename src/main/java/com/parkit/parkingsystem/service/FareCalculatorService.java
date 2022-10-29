package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.UserUtils;

public class FareCalculatorService {
	
	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		
		UserUtils userUtils = new UserUtils();
		// No reduction = set multiplicator reduction to 1
		double recurringUserReductionMultiplicator = 1;
		if(userUtils.isRecurringUser(ticket.getVehicleRegNumber())) {
			// Reduction = 5% less on total price
			recurringUserReductionMultiplicator = 0.95;
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		float durationMillis = outHour - inHour;
		float durationHour = durationMillis / 1000 / 60 / 60; // Convert from milliseconds to hours

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
		} else {  // Parking fare over 30 minutes
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(durationHour * Fare.CAR_RATE_PER_HOUR * recurringUserReductionMultiplicator);
				break;
			}
			case BIKE: {
				ticket.setPrice(durationHour * Fare.BIKE_RATE_PER_HOUR * recurringUserReductionMultiplicator);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}
}
