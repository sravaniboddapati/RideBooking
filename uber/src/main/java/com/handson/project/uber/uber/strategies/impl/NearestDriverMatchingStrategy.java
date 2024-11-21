package com.handson.project.uber.uber.strategies.impl;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.RideRequest;
import com.handson.project.uber.uber.repositories.DriverRespository;
import com.handson.project.uber.uber.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NearestDriverMatchingStrategy implements DriverMatchingStrategy {

    private final DriverRespository driverRespository;
    private final ModelMapper modelMapper;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        return driverRespository.findNearestDrivers(rideRequest.getPickupLocation());
    }
}
