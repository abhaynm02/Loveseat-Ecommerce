package com.Abhay.Loveseat.Dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "please enter the name !!! ")
    @Length(min = 3, max = 20)
    @Pattern(regexp = "[A-Z][a-z]*",message = "only letters are allowed")
    private String name;
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",message = "enter a proper email")
    private String email;
    @NotBlank(message = "must be 8 characters !!!")
    @Length(min = 8, max = 20)
    private String password;
    @NotNull(message = "Phone number is required.")
    @Size(min = 10, max = 13, message = "Phone number must be between 10 and 13 characters long.")
    @Pattern(regexp = "^[0-9+]+$", message = "Only numbers and '+' are allowed")
    private String phone;
    @NotBlank(message = "re-enter your password!!")
    private String repeatPassword;

    public UserDto() {
    }

    public UserDto(String name, String email, String password, String phone, String repeatPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.repeatPassword = repeatPassword;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                '}';
    }
}
