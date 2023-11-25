package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.UserDto;

public interface UserService {
    void save (UserDto user);
    boolean verifyAccount(String email, String otp);
}
