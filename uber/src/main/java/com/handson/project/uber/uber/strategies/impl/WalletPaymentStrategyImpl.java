package com.handson.project.uber.uber.strategies.impl;

import com.handson.project.uber.uber.entities.*;
import com.handson.project.uber.uber.entities.enums.PaymentMethod;
import com.handson.project.uber.uber.entities.enums.TransactionMethod;
import com.handson.project.uber.uber.service.RideService;
import com.handson.project.uber.uber.service.WalletService;
import com.handson.project.uber.uber.strategies.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategyImpl implements WalletPaymentStrategy {

    private final RideService rideService;
    private final WalletService walletService;

    @Override
    public void processPayment(Payment payment) {
        Ride ride = payment.getRide();
        Driver driver = ride.getDriver();
        Rider rider = ride.getRider();
        Wallet wallet = walletService.findByUser(rider.getUser());
        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, ride, TransactionMethod.RIDE);

        walletService.addMoneyToWallet(driver.getUser(), (1- paymentCommission)*payment.getAmount(), null, ride, TransactionMethod.RIDE);
    }
}
