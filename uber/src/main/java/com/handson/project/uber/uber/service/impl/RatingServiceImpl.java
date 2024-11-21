package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.Rating;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.Rider;
import com.handson.project.uber.uber.exceptions.ResourceNotFoundException;
import com.handson.project.uber.uber.repositories.RatingRepository;
import com.handson.project.uber.uber.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    @Override
    public RiderDto rateRider(Ride ride, int rating) {
        Rider rider = ride.getRider();
        Rating ratingtemp = ratingRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        if(ratingtemp.getRiderRating() != null) {
            throw new RuntimeException("Rider is already rated");
        }
        ratingtemp.setRiderRating(rating);
        ratingRepository.save(ratingtemp);

        Double updatedRating = ratingRepository.findByRider(rider).stream()
                .mapToDouble(Rating::getDriverRating).average().orElse(0.0);
        rider.setRating(updatedRating);
        RiderDto riderDto = modelMapper.map(rider, RiderDto.class);


        return riderDto;
    }

    @Override
    public DriverDto rateDriver(Ride ride, int rating) {
       Driver driver = ride.getDriver();
       Rating ratingtemp = ratingRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
       if(ratingtemp.getDriverRating() != null) {
           throw new RuntimeException("Driver is already rated");
       }
       ratingtemp.setDriverRating(rating);
       ratingRepository.save(ratingtemp);

       Double updatedRating = ratingRepository.findByDriver(driver).stream().mapToDouble(Rating::getDriverRating).average().orElse(0.0);
       driver.setRating(updatedRating);
       DriverDto driverdto = modelMapper.map(driver, DriverDto.class);
       return driverdto;
    }

    @Override
    public void createNewRating(Ride ride) {
       Rating rating =  Rating.builder()
                .ride(ride)
                .rider(ride.getRider())
                .driver(ride.getDriver()).build();
       ratingRepository.save(rating);
    }
}
