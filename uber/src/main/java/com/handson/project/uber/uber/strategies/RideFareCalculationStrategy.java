package com.handson.project.uber.uber.strategies;

import com.handson.project.uber.uber.entities.RideRequest;

public interface RideFareCalculationStrategy {

    public static final int defaultFare = 10;
    double calculateFare(RideRequest rideRequest);
}
