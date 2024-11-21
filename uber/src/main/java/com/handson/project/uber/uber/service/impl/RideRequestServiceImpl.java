package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.entities.RideRequest;
import com.handson.project.uber.uber.exceptions.ResourceNotFoundException;
import com.handson.project.uber.uber.repositories.RideRequestRepository;
import com.handson.project.uber.uber.service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
      RideRequest rideRequest = rideRequestRepository.findById(rideRequestId).orElseThrow(() -> new ResourceNotFoundException("RideRequest not found"));
        return rideRequest;
    }

    @Override
    public void update(RideRequest rideRequest) {
       RideRequest rideRequest1 = findRideRequestById(rideRequest.getId());
       rideRequestRepository.save(rideRequest1);
    }
}
