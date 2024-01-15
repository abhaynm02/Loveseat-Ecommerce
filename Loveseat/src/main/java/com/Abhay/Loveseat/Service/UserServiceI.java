package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.UserDto;
import com.Abhay.Loveseat.Email.EmailUtil;
import com.Abhay.Loveseat.Model.PasswordRestToken;
import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Otp.OtpUtil;
import com.Abhay.Loveseat.Repository.PasswordRestTokenRepo;
import com.Abhay.Loveseat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class UserServiceI implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private PasswordRestTokenRepo passwordRestTokenRepo;
    @Autowired
     private WalletServiceI walletServiceI;
      private static final double REFERAl_BONES=500;
      private  static  final double REFERRED_BONES=300;
    @Override
    public void save(UserDto user,String referLink) {
        String encodePassword =passwordEncoder.encode(user.getPassword());
       final double JOIN_BONES=100;

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
         if (referLink!=null){
             walletServiceI.createOrUpdateWallet(userEntity,REFERRED_BONES);
             walletServiceI.createOrUpdateWallet(findByReferCode(referLink),REFERAl_BONES);
         }else {
             walletServiceI.createOrUpdateWallet(userEntity,JOIN_BONES);
         }



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
    public UserDto userProfile( String email){
        UserEntity user= userRepository.findByEmail(email);
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPhone(user.getPhone());
        return userDto;

    }

    @Override
    public void saveChange(UserDto user,String email) {
        UserEntity changeUser=userRepository.findByEmail(email);
        changeUser.setName(user.getName());
        changeUser.setPhone(user.getPhone());
        userRepository.save(changeUser);
    }
    public  void forgotPassword(String email){
        String restLink=generateRestToken(email);
        emailUtil.recoveryLink(email,restLink);

    }

    @Override
    public boolean hasExpipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentTime=LocalDateTime.now();
        return expiryDateTime.isAfter(currentTime);

    }

    @Override
    public void changePassword(String password, String email) {
        UserEntity user=userRepository.findByEmail(email);
        String encodePassword=passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        userRepository.save(user);

    }

    @Override
    public boolean passwordIsCorrect(String oldPassword, String email ,String newPassword) {
       UserEntity user= userRepository.findByEmail(email);

       if (user!=null&& passwordEncoder.matches(oldPassword,user.getPassword())){
           user.setPassword(passwordEncoder.encode(newPassword));
           userRepository.save(user);
           return  true;
       }
        return false;
    }

    @Override
    public void setReferralLin(UserEntity user) {
        userRepository.save(user);
    }

    private String generateRestToken(String email) {
        UUID uuid=UUID.randomUUID();
        LocalDateTime currentTime=LocalDateTime.now();
        LocalDateTime expiryDateTime=currentTime.plusMinutes(20);
        PasswordRestToken restToken=new PasswordRestToken();
        restToken.setUser(userRepository.findByEmail(email));
        restToken.setToken(uuid.toString());
        restToken.setExpiryDateTime(expiryDateTime);
        PasswordRestToken token=passwordRestTokenRepo.save(restToken);
      if (token!=null){
          String endpoindUrl="http://localhost:8080/resetPassword";
          return endpoindUrl+"/"+restToken.getToken();
      }
      return "";
    }
    public UserEntity findByReferCode(String referCode){
       UserEntity user= userRepository.findByReferCode(referCode);
        return user;
    }
}
