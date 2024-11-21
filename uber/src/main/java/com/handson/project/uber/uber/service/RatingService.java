package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.Ride;

public interface RatingService {
    RiderDto rateRider(Ride ride, int rating);

    DriverDto rateDriver(Ride ride, int rating);

    void createNewRating(Ride ride);
}
