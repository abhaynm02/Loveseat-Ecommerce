package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.AddressDto;
import com.Abhay.Loveseat.Model.Address;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public AddressDto findById(Long id) {
        Optional<Address> addressOptional= addressRepository.findById(id);
        if ( addressOptional.isPresent()) {
            Address address=addressOptional.get();
            AddressDto addressDto=new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setLastName(address.getLastName());
            addressDto.setFirstName(address.getFirstName());
            addressDto.setPhone(address.getPhone());
            addressDto.setPin(address.getPin());
            addressDto.setCity(address.getCity());
            addressDto.setHomeAddress(address.getHomeAddress());
            addressDto.setDefaultAddress(address.isDefaultAddress());
            return addressDto;

        }
        return  null;
    }

    @Override
    public void saveEdits(Long id, AddressDto addressDto) {
       Optional<Address> addressOptional=addressRepository.findById(id);
       if(addressDto.isDefaultAddress()){
           addressRepository.updateOtherAddressesToNotDefault(addressDto.getId());

       }
       if (addressOptional.isPresent()){
           Address address=addressOptional.get();
           address.setFirstName(addressDto.getFirstName());
           address.setLastName(addressDto.getLastName());
           address.setPhone(address.getPhone());
           address.setPin(addressDto.getPin());
           address.setCity(addressDto.getCity());
           address.setHomeAddress(addressDto.getHomeAddress());
           address.setDefaultAddress(addressDto.isDefaultAddress());
           addressRepository.save(address);

       }

    }
    public  Address findAddress(Long id){
        return addressRepository.findById(id).get();
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
