package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.RideRequest;
import com.handson.project.uber.uber.entities.Rider;
import com.handson.project.uber.uber.entities.enums.RideRequestStatus;
import com.handson.project.uber.uber.entities.enums.RideStatus;
import com.handson.project.uber.uber.exceptions.ResourceNotFoundException;
import com.handson.project.uber.uber.repositories.RideRepository;
import com.handson.project.uber.uber.service.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride Id not found"));
        return ride;
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.ACCEPTED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);

        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
