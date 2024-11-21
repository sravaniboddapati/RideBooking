package com.handson.project.uber.uber.dto;

import com.handson.project.uber.uber.entities.Rider;
import com.handson.project.uber.uber.entities.enums.PaymentMethod;
import com.handson.project.uber.uber.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideRequestDto {

    private Long id;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethod paymentMethod;
    private RideRequestStatus rideRequestStatus;
    private Double fare;
}
