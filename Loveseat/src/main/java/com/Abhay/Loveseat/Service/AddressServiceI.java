package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.AddressDto;
import com.Abhay.Loveseat.Model.Address;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceI implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserServiceI userServiceI;

    @Override
    public void save(AddressDto addressDto, String email) {
        UserEntity user=userServiceI.findByEmail(email);
        Address address=new Address();
        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setCity(addressDto.getCity());
        address.setPhone(addressDto.getPhone());
        address.setPin(addressDto.getPin());
        address.setHomeAddress(addressDto.getHomeAddress());
        address.setDefaultAddress(addressDto.isDefaultAddress());
        address.setUser(user);
        addressRepository.save(address);

    }
}
