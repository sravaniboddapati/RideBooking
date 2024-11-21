package com.handson.project.uber.uber.strategies;

import com.handson.project.uber.uber.strategies.impl.HighRatedDriverMatchingStrategy;
import com.handson.project.uber.uber.strategies.impl.NearestDriverMatchingStrategy;
import com.handson.project.uber.uber.strategies.impl.SurgePricingFareCalcStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {
    private final NearestDriverMatchingStrategy nearestDriverMatchingStrategy;
    private final HighRatedDriverMatchingStrategy highRatedDriverMatchingStrategy;
    private final SurgePricingFareCalcStrategy surgePricingFareCalculationStrategy;
    private final RideFareCalculationStrategy defaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating >4.5 ){
            return highRatedDriverMatchingStrategy;
        }
        else
            return nearestDriverMatchingStrategy;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {

//        6PM to 9PM is SURGE TIME
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime) {
            return surgePricingFareCalculationStrategy;
        } else {
            return defaultFareCalculationStrategy;
        }
    }
}
