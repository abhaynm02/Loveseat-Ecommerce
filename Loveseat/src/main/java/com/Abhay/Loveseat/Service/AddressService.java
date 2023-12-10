package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.AddressDto;

public interface AddressService {
    void save(AddressDto addressDto,String email);
}
