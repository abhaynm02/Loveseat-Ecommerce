package com.Abhay.Loveseat.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class AddressDto {
    private Long id;
    @NotBlank(message = "please enter your name!!")
    @Length(min = 1,max = 20)
    @Pattern(regexp = "[A-Z][a-z]*",message = "only letters are allowed" )
    private String firstName;
    @NotBlank(message = "please enter your name!!")
    @Length(min = 1,max = 20)
    @Pattern(regexp = "[a-z]*",message = "only letters are allowed" )
    private String lastName;
    @NotNull(message = "Phone number is required.")
    @Size(min = 10, max = 13, message = "Phone number must be between 10 and 13 characters long.")
    @Pattern(regexp = "^[0-9+]+$", message = "Only numbers and '+' are allowed")
    private String phone;
    @NotBlank(message = "please enter your address")
    private  String homeAddress;
    @NotBlank(message = "please enter your address")
    private String city;
    @NotBlank(message = "enter your pin code")
    @Pattern(regexp ="^[0-9]+$", message = "Only numbers " )
    private String pin;
    private  boolean defaultAddress;

    public AddressDto() {
    }

    public AddressDto(Long id, String firstName, String lastName, String phone, String homeAddress, String city, String pin, boolean defaultAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.homeAddress = homeAddress;
        this.city = city;
        this.pin = pin;
        this.defaultAddress = defaultAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", city='" + city + '\'' +
                ", pin='" + pin + '\'' +
                ", defaultAddress=" + defaultAddress +
                '}';
    }
}
