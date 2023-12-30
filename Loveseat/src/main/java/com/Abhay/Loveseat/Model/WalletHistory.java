package com.Abhay.Loveseat.Model;

import com.Abhay.Loveseat.Enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private  double amount;
    private LocalDateTime dateTime;
    private WalletTransactionType transactionType;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id",referencedColumnName = "id")
    private Wallet wallet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public WalletTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(WalletTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
