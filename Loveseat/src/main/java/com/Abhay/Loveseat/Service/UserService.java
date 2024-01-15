package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Model.UserEntity;

import java.time.LocalDateTime;

public interface UserService {
    void save (UserDto user,String referLink);
//    boolean verifyAccount(String email, String otp);

    boolean isEmailExists(String email);
//    void resentOtp(String email);
    UserDto userProfile(String email);
    void saveChange(UserDto user,String email);
    void forgotPassword(String email);

    boolean hasExpipred(LocalDateTime expiryDateTime);

    void changePassword(String password, String email);

    boolean passwordIsCorrect(String oldPassword, String email ,String newPassword);

    void setReferralLin(UserEntity user);
}
