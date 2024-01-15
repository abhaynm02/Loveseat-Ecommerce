package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Enums.WalletTransactionType;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Model.Wallet;
import com.Abhay.Loveseat.Model.WalletHistory;
import com.Abhay.Loveseat.Repository.UserRepository;
import com.Abhay.Loveseat.Repository.WalletHistoryRepository;
import com.Abhay.Loveseat.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class WalletServiceI implements WalletService{
//    @Autowired
//     private  UserServiceI userServiceI;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletHistoryRepository walletHistoryRepository;
    @Transactional
    @Override
    public void createOrUpdateWallet(UserEntity user, double amount) {
        Wallet wallet=user.getWallet();
        if (wallet ==null){
            wallet =new Wallet();
            wallet.setUser(user);
        }
        Set<WalletHistory> walletHistorySet=wallet.getWalletHistories();
        if (walletHistorySet==null){
            walletHistorySet=new HashSet<>();
            WalletHistory walletHistory=new WalletHistory();
            walletHistory.setAmount(amount);
            walletHistory.setDateTime(LocalDateTime.now());
            walletHistory.setWallet(wallet);
            walletHistory.setTransactionType(WalletTransactionType.CREDIT);
            walletHistoryRepository.save(walletHistory);
            walletHistorySet.add(walletHistory);
            walletHistory.setWallet(wallet);
            walletHistoryRepository.save(walletHistory);

        }else {
            WalletHistory walletHistory=new WalletHistory();
            walletHistory.setAmount(amount);
            walletHistory.setDateTime(LocalDateTime.now());
            walletHistory.setWallet(wallet);
            walletHistory.setTransactionType(WalletTransactionType.CREDIT);
            walletHistorySet.add(walletHistory);
            walletHistoryRepository.save(walletHistory);
        }
        wallet.setWalletHistories(walletHistorySet);
        wallet.setTotalAmount(wallet.getTotalAmount()+amount);
        walletRepository.save(wallet);


    }

    @Override
    public Page<WalletHistory> transactionHistory(Pageable pageable, long id) {
        return walletHistoryRepository.transactionHistory(id,pageable);
    }

    @Override
    public void walletPaymentInOrder(double couponDiscount, UserEntity user) {
        Wallet wallet=user.getWallet();
        Set<WalletHistory>walletHistorySet=wallet.getWalletHistories();
        WalletHistory walletHistory=new WalletHistory();
        walletHistory.setAmount(couponDiscount);
        walletHistory.setDateTime(LocalDateTime.now());
        walletHistory.setWallet(wallet);
        walletHistory.setTransactionType(WalletTransactionType.DEBIT);
        walletHistorySet.add(walletHistory);
        walletHistoryRepository.save(walletHistory);

        wallet.setTotalAmount(wallet.getTotalAmount()-couponDiscount);
        wallet.setWalletHistories(walletHistorySet);
        walletRepository.save(wallet);

    }

    public String ReferLink(UserEntity user){
        String link= user.getReferLink();
        if (link==null){
            link= generateReferralCode();
            user.setReferLink(link);
//            userServiceI.setReferralLin(user);
            userRepository.save(user);
        }

        return "http://localhost:8080/register?link="+link;
    }

    private String generateReferralCode() {
        Random random = new Random();
        int length = 6; // length of the random code
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            randomCode.append(alphabet.charAt(index));
        }
        return randomCode.toString();
    }

}
