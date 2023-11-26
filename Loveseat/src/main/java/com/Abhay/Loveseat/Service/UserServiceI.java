package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Email.EmailUtil;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Otp.OtpUtil;
import com.Abhay.Loveseat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
@Service
public class UserServiceI implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private OtpUtil otpUtil;
    @Override
    public void save(UserDto user) {
        String otp=otpUtil.generateOtp();
        String encodePassword =passwordEncoder.encode(user.getPassword());
        UserEntity userEntity =new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setRole("ADMIN");
        userEntity.setStatus(true);
        userEntity.setOtp(otp);
        userEntity.setOtpGenerateTime(LocalTime.now());
        emailUtil.sentOtpEmail(user.getEmail(),otp);
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(encodePassword);
        userRepository.save(userEntity);

    }

    @Override
    public boolean verifyAccount(String email, String otp) {
        UserEntity user=userRepository.findByEmail(email);
        if (user.getOtp().equals(otp)&& Duration.between(user.getOtpGenerateTime(),
                LocalTime.now()).getSeconds()<(2*60)){
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public void resentOtp(String email) {
        String resendOtp=otpUtil.generateOtp();
        UserEntity user=userRepository.findByEmail(email);
        user.setOtp(resendOtp);
        emailUtil.sentOtpEmail(email,resendOtp);
        userRepository.save(user);


    }
}
