package com.Abhay.Loveseat.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sentOtpEmail(String email,String otp){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email  verification  ");
        message.setText("its your OTP to Login"+otp);
        javaMailSender.send(message);
    }
    public  void recoveryLink(String email,String link){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email  verification  ");
        message.setText("its your password rest link "+link);
        javaMailSender.send(message);
    }
}
