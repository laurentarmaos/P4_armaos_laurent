package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	double discount = 1;
	
    public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        double inHour = ticket.getInTime().getTime();
        double outHour = ticket.getOutTime().getTime();
        
        double duration = (outHour - inHour) / (60.000 * 60.000 * 1000.000);
        
        if(duration <= 0.5) {
        	ticket.setPrice(0);
        }else {
        	switch (ticket.getParkingSpot().getParkingType()){
        	case CAR: {
        		ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * discount);
        		break;
        	}
        	case BIKE: {
        		ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * discount);
        		break;
        	}
        	default: throw new IllegalArgumentException("Unkown Parking Type");
        	}
        }

    }
}