package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.Wallet;
import com.handson.project.uber.uber.entities.WalletTransaction;

public interface WalletTransactionService {

    WalletTransaction createNewWalletTransaction(Wallet wallet, double amount, Ride ride);
}
