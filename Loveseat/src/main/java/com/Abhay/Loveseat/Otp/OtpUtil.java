package com.Abhay.Loveseat.Otp;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {
    public String generateOtp(){
        Random random =new Random();
        int randomNumber =random.nextInt(999999);
        String outPut=Integer.toString(randomNumber);
        while (outPut.length()<6){
            outPut="0"+ outPut;
        }
        return  outPut;
    }
}
