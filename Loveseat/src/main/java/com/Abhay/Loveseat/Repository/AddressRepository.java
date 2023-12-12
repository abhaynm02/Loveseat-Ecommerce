package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<Address,Long> {
   @Transactional
   @Modifying
   @Query("UPDATE Address a SET a.defaultAddress = false WHERE a.id <> :addressId")
   void updateOtherAddressesToNotDefault(@Param("addressId") Long addressId);
    @Modifying
    @Query("UPDATE Address a SET a.defaultAddress = true WHERE a.id = :addressId")
    void updateDefaultAddress(@Param("addressId") Long addressId);


}
