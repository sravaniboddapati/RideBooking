package com.handson.project.uber.uber.strategies;

import com.handson.project.uber.uber.entities.enums.PaymentMethod;

public interface CashPaymentStrategy {
    void processPayment(PaymentMethod paymentMethod);
}
