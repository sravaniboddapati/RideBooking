package com.handson.project.uber.uber.strategies;

import com.handson.project.uber.uber.entities.Payment;
import com.handson.project.uber.uber.entities.enums.PaymentMethod;


public interface WalletPaymentStrategy {

    final double paymentCommission = 0.01;
    void processPayment(Payment payment);
}
