package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.OrderItem;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Model.WalletHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    void createOrUpdateWallet(UserEntity user, double amount);
  Page<WalletHistory> transactionHistory(Pageable pageable, long id);
}
