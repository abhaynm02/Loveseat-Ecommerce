package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.AddressDto;

import java.util.Optional;

public interface AddressService {
    void save(AddressDto addressDto,String email);
   AddressDto findById(Long id);
   void saveEdits(Long id,AddressDto addressDto);
   void  deleteAddress(Long id);
}
