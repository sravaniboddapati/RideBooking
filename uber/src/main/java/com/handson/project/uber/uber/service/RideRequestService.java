package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.entities.RideRequest;

public interface RideRequestService {
    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
