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

    @Override
    public void save(UserDto user) {
        String encodePassword =passwordEncoder.encode(user.getPassword());
        UserEntity userEntity =new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setRole("USER");
        userEntity.setStatus(true);
        userEntity.setActive(true);
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(encodePassword);
        userRepository.save(userEntity);

    }

    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }


    public void SendOpt(String email,String otp){

        emailUtil.sentOtpEmail(email,otp);
    }
    public  UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
