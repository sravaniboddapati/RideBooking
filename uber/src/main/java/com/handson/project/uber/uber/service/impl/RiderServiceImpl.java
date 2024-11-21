package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RideDto;
import com.handson.project.uber.uber.dto.RideRequestDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.*;
import com.handson.project.uber.uber.entities.enums.RideRequestStatus;
import com.handson.project.uber.uber.entities.enums.RideStatus;
import com.handson.project.uber.uber.exceptions.ResourceNotFoundException;
import com.handson.project.uber.uber.repositories.RideRepository;
import com.handson.project.uber.uber.repositories.RideRequestRepository;
import com.handson.project.uber.uber.repositories.RiderRepository;
import com.handson.project.uber.uber.service.DriverService;
import com.handson.project.uber.uber.service.RatingService;
import com.handson.project.uber.uber.service.RideService;
import com.handson.project.uber.uber.service.RiderService;
import com.handson.project.uber.uber.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final DriverService driverService;
    private final RiderRepository riderRepository;
    private final RatingService ratingService;
    private final RideService rideService;

    @Override
    public RideRequestDto createRideRequest(RideRequestDto rideRequestDto) {
        //to check if it is rider or user
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRider(rider);

        rideRequest.setFare(rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest));
        rideRequest.setRideRequestStatus(RideRequestStatus.AWAITING);
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        RideRequestDto savedRideRequestDto = modelMapper.map(savedRideRequest, RideRequestDto.class);

        return savedRideRequestDto;
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Rider rider = getCurrentRider();
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        if (ride.getRider() != rider)
            throw new RuntimeException("Not a valid rider to cancel ride");
        ride.setRideStatus(RideStatus.CANCELLED);
        driverService.setDriverAvailability(ride.getDriver(), true);
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Rider rider = getCurrentRider();
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        if (ride.getRider() != rider)
            throw new RuntimeException("Not a valid rider to cancel ride");
        if(ride.getRideStatus() != RideStatus.ENDED)
            throw new RuntimeException("Invalid Ride status to rate driver");
        ratingService.rateDriver(ride, rating);

        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        RiderDto riderDto = modelMapper.map(rider, RiderDto.class);

        return riderDto;
    }

    @Override
    public Page<RideDto> getAllmyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        Page<Ride> rides = rideService.getAllRidesOfRider(rider, pageRequest);
        return  rideService.getAllRidesOfRider(rider, pageRequest).map(ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder().user(user).rating(0.0).build();
        rider = riderRepository.save(rider);
        return rider;
    }
}
