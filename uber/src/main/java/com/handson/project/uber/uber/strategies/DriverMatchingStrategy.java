package com.handson.project.uber.uber.strategies;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
