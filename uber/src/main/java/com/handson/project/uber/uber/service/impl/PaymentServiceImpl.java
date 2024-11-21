package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.entities.Payment;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.enums.PaymentStatus;
import com.handson.project.uber.uber.repositories.PaymentRepository;
import com.handson.project.uber.uber.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                        .ride(ride)
                        .paymentMethod(ride.getPaymentMethod())
                        .amount(ride.getFare())
                        .paymentStatus(PaymentStatus.PENDING)
                        .paymentTime(LocalDateTime.now())
                        .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void processPayment(Payment payment) {

    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {

    }
}
