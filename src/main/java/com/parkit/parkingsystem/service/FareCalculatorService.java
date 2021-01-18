package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

      
        long duration = outHour - inHour;
        float durationInMinutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(durationInMinutes * Fare.CAR_RATE_PER_HOUR/60);
                break;
            }
            case BIKE: {
                ticket.setPrice(durationInMinutes * Fare.BIKE_RATE_PER_HOUR/60);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}