package com.handson.project.uber.uber.service;


import com.handson.project.uber.uber.entities.Payment;
import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.enums.PaymentStatus;

public interface PaymentService {

    Payment createNewPayment(Ride ride);

    void processPayment(Payment payment);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
