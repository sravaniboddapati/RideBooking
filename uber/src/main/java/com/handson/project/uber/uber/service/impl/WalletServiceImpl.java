package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.User;
import com.handson.project.uber.uber.entities.Wallet;
import com.handson.project.uber.uber.entities.WalletTransaction;
import com.handson.project.uber.uber.entities.enums.TransactionMethod;
import com.handson.project.uber.uber.entities.enums.TransactionType;
import com.handson.project.uber.uber.repositories.WalletRepository;
import com.handson.project.uber.uber.service.WalletService;
import com.handson.project.uber.uber.service.WalletTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = walletTransactionService.createNewWalletTransaction(wallet, amount, ride);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction = walletTransactionService.createNewWalletTransaction(wallet, amount, ride);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return null;
    }

    @Override
    public Wallet createNewWallet(User user) {
        return null;
    }

    @Override
    public Wallet findByUser(User user) {
        return null;
    }
}
