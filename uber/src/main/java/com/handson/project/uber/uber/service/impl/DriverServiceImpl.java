package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RideDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.*;
import com.handson.project.uber.uber.entities.enums.RideRequestStatus;
import com.handson.project.uber.uber.entities.enums.RideStatus;
import com.handson.project.uber.uber.exceptions.ResourceNotFoundException;
import com.handson.project.uber.uber.repositories.DriverRespository;
import com.handson.project.uber.uber.repositories.RideRepository;
import com.handson.project.uber.uber.repositories.RideRequestRepository;
import com.handson.project.uber.uber.service.DriverService;
import com.handson.project.uber.uber.service.RatingService;
import com.handson.project.uber.uber.service.RideRequestService;
import com.handson.project.uber.uber.service.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestRepository rideRequestRepository;
    private final RideRequestService rideRequestService;
    private final DriverRespository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RatingService ratingService;


    @Override
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if(rideRequest.getRideRequestStatus() != RideRequestStatus.AWAITING){
            throw new RuntimeException("Ride is not in pending status");
        }
        Driver driver = getCurrentDriver();
        if(!driver.getAvailable()){
            throw new RuntimeException("Driver isn't available");
        }
        setDriverAvailability(driver, false);
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = rideService.createNewRide(rideRequest, driver);

        rideRequestService.update(rideRequest);
        RideDto rideDto = modelMapper.map(ride, RideDto.class);

        return rideDto;
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        if(ride.getDriver() != getCurrentDriver()){
            throw new RuntimeException("Invalid driver");
        }
        if(ride.getRideStatus() != RideStatus.ACCEPTED){
            throw new RuntimeException("Invalid Ride status "+ride.getRideStatus());
        }
        if(!ride.getOtp().equals(otp) ){
            throw new RuntimeException("OTP is not matching");
        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        if(ride.getDriver() != getCurrentDriver()){
            throw new RuntimeException("Invalid driver");
        }
        if(ride.getRideStatus() != RideStatus.ACCEPTED){
            throw new RuntimeException("Invalid Ride status "+ride.getRideStatus());
        }

        Ride updatedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        return modelMapper.map(updatedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        if(ride.getDriver() != getCurrentDriver()){
            throw new RuntimeException("Invalid driver");
        }
        if(ride.getRideStatus() != RideStatus.ONGOING){
            throw new RuntimeException("Invalid Ride status "+ride.getRideStatus());
        }
        setDriverAvailability(ride.getDriver(), true);
        ride.setEndedAt(LocalDateTime.now());
        Ride endedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        return modelMapper.map(endedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        if(ride.getDriver() != getCurrentDriver()){
            throw new RuntimeException("Invalid driver");
        }
        if(ride.getRideStatus() != RideStatus.ENDED){
            throw new RuntimeException("Invalid Ride status "+ride.getRideStatus());
        }

        return ratingService.rateRider(ride, rating);

    }

    @Override
    public DriverDto getMyProfile() {
        Driver driver = getCurrentDriver();
        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllmyRides(PageRequest pageRequest) {
        Driver driver = getCurrentDriver();
        Page<Ride> rides = rideService.getAllRidesOfDriver(driver, pageRequest);
        return  rideService.getAllRidesOfDriver(driver, pageRequest).map(ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public void setDriverAvailability(Driver driver, boolean b) {
        driver.setAvailable(b);
        driverRepository.save(driver);
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with " +
                        "id "+user.getId()));
    }

    @Override
    public void createNewDriver(Driver createDriver) {
        driverRepository.save(createDriver);
    }
}
