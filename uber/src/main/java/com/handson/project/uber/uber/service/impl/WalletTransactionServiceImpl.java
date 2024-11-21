package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.entities.Ride;
import com.handson.project.uber.uber.entities.Wallet;
import com.handson.project.uber.uber.entities.WalletTransaction;
import com.handson.project.uber.uber.entities.enums.TransactionMethod;
import com.handson.project.uber.uber.entities.enums.TransactionType;
import com.handson.project.uber.uber.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Override
    public WalletTransaction createNewWalletTransaction(Wallet wallet, double amount, Ride ride) {
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .amount(amount)
                .ride(ride)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(TransactionMethod.RIDE).build();
        wallet.setTransactions((List<WalletTransaction>) walletTransaction);
        return walletTransaction;
    }
}
