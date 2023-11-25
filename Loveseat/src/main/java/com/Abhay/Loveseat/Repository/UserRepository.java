package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
      UserEntity findByEmail(String email);
      boolean existsByEmail(String email);

}
