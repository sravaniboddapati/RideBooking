package com.handson.project.uber.uber.repositories;

import com.handson.project.uber.uber.entities.Driver;
import com.handson.project.uber.uber.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRespository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT d.*,ST_Distance(d.current_location, :pickupLocation) AS dist from driver d "
            +"WHERE ST_DWithin(d.currentLocation, :pickUpLocation, 10000) "
            +"and d.available = true "
            +"ORDER BY dist LIMIT 10", nativeQuery = true)
    List<Driver> findNearestDrivers(Point pickUpLocation);

    @Query(value = "SELECT d.* FROM driver d "
            +"WHERE ST_DWithin(d.current_location, :pickUpLocation, 15000) and d.rating > 4.5 "
            +"and d.available = true "
            +"ORDER BY d.rating desc LIMIT 10", nativeQuery = true)
    List<Driver> findNearestHighestRatedDrivers(Point pickUpLocation);

    Optional<Driver> findByUser(User user);
}
