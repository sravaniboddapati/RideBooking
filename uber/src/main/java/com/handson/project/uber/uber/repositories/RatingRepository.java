package com.handson.project.uber.uber.repositories;

import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.Rating;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
