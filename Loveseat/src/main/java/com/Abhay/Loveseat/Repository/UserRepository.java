package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
      UserEntity findByEmail(String email);
      boolean existsByEmail(String email);
      @Modifying
      @Query("UPDATE UserEntity u SET u.active= :status WHERE u.id= :userId")
      void updateStatus(Long userId,boolean status);
      @Query("SELECT u FROM UserEntity u WHERE u.name LIKE ?1%")
      Page<UserEntity> searchByName(String startingLetter, Pageable pageable);
}
