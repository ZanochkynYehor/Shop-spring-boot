package com.example.shopspringboot.bean;

import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.validation.annotation.PasswordMatches;
import com.example.shopspringboot.validation.annotation.ValidEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@PasswordMatches(message = "{signup.error.confirm_password}")
public class SignUpFormBean {
    @Pattern(regexp = "(?iu)[a-zа-яёіїє0-9'-]+", message = "{signup.error.first_name}")
    private String firstName;

    @Pattern(regexp = "(?iu)[a-zа-яёіїє0-9'-]+", message = "{signup.error.last_name}")
    private String lastName;

    @ValidEmail(message = "{signup.error.email}")
    private String email;

    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,20}", message = "{signup.error.password}")
    private String password;
    private String confirmPassword;

    @NotNull
    private boolean receiveNewsletters;

    private String captcha;
    private MultipartFile avatar;

    public SignUpFormBean() {
        receiveNewsletters = true;
    }

    public SignUpFormBean(String first, String last, String email, String password,
                          String confirmPassword, boolean newsletters, String captcha,
                          MultipartFile avatar) {
        firstName = first;
        lastName = last;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        receiveNewsletters = newsletters;
        this.captcha = captcha;
        this.avatar = avatar;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isReceiveNewsletters() {
        return receiveNewsletters;
    }

    public void setReceiveNewsletters(boolean receiveNewsletters) {
        this.receiveNewsletters = receiveNewsletters;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return new User(firstName, lastName, email, password, receiveNewsletters);
    }

    @Override
    public String toString() {
        return "SignUpFormBean{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", receiveNewsletters=" + receiveNewsletters +
                ", captcha='" + captcha + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}