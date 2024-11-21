package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RideDto;
import com.handson.project.uber.uber.dto.RideRequestDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.Rider;
import com.handson.project.uber.uber.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

    RideRequestDto createRideRequest(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);


    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllmyRides(PageRequest pageRequest);

    Rider getCurrentRider();

    Rider createNewRider(User user);
}
