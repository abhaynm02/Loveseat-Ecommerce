package com.Abhay.Loveseat.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @Column(name = "totalAmount", columnDefinition = "Decimal(10,2)")
    private double totalAmount;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "wallet")
    private Set<WalletHistory> walletHistories;
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Wallet(){
        this.totalAmount=0.00;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<WalletHistory> getWalletHistories() {
        return walletHistories;
    }

    public void setWalletHistories(Set<WalletHistory> walletHistories) {
        this.walletHistories = walletHistories;
    }
}
