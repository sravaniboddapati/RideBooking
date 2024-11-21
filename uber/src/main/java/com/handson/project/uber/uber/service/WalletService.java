package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.User;
import com.handson.project.uber.uber.entities.Wallet;
import com.handson.project.uber.uber.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount,
                            String transactionId, Ride ride,
                            TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount,
                                 String transactionId, Ride ride,
                                 TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
