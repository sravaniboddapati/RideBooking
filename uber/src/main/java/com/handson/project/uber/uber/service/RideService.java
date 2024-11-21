package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.RideRequest;
import com.handson.project.uber.uber.entities.Rider;
import com.handson.project.uber.uber.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {
    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
