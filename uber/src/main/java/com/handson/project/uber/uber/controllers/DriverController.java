package com.handson.project.uber.uber.controllers;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.RideDto;
import com.handson.project.uber.uber.dto.RiderDto;
import com.handson.project.uber.uber.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@Secured("ROLE_DRIVER")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideId}/otp/{otp}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideId,@PathVariable String otp){
       return ResponseEntity.ok(driverService.startRide(rideId, otp) );
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId) );
    }
    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId) );
    }

    @PostMapping("/rateRider/{rideId}/rating")
    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId, @RequestParam Integer rating){
        return ResponseEntity.ok(driverService.rateRider(rideId, rating) );
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile() );
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDto>> getAllmyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet, pageSize);
        return ResponseEntity.ok(driverService.getAllmyRides(pageRequest) );
    }
}
