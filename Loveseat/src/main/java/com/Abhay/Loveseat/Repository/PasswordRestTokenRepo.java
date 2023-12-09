package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.PasswordRestToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRestTokenRepo extends JpaRepository<com.Abhay.Loveseat.Model.PasswordRestToken,Long> {
    PasswordRestToken findByToken(String token);
}
