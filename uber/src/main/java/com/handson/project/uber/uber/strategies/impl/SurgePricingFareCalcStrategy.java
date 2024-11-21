package com.handson.project.uber.uber.strategies.impl;

import com.handson.project.uber.uber.entities.RideRequest;
import com.handson.project.uber.uber.service.DistanceService;
import com.handson.project.uber.uber.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurgePricingFareCalcStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final int surgeFactor = 2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double dist = distanceService.calculateDistance(rideRequest.getPickupLocation(), rideRequest.getDropOffLocation());
        return dist * defaultFare * 2;
    }
}