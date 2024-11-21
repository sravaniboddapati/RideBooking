package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RideDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);
    RideDto startRide(Long rideId, String otp);
    RideDto cancelRide(Long rideId);
    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllmyRides(PageRequest pageRequest);

    void setDriverAvailability(Driver driver, boolean b);

    Driver getCurrentDriver();

    void createNewDriver(Driver createDriver);
}
