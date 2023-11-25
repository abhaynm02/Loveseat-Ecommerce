package com.Abhay.Loveseat.Model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "UserEntity",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private  String email;
    private String password;
    private String role;
    private String phone;
    private  String otp;
    private LocalTime otpGenerateTime;
    private  boolean active;
    private  boolean status;

    public UserEntity() {
    }

    public UserEntity(long id, String name, String email,
                      String password, String role, String phone,
                      String otp, LocalTime otpGenerateTime,
                      boolean active, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.otp = otp;
        this.otpGenerateTime = otpGenerateTime;
        this.active = active;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalTime getOtpGenerateTime() {
        return otpGenerateTime;
    }

    public void setOtpGenerateTime(LocalTime otpGenerateTime) {
        this.otpGenerateTime = otpGenerateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", otp='" + otp + '\'' +
                ", otpGenerateTime=" + otpGenerateTime +
                ", active=" + active +
                ", status=" + status +
                '}';
    }
}
