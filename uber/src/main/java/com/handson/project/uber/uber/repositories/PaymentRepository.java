package com.handson.project.uber.uber.repositories;

import com.handson.project.uber.uber.entities.Payment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
