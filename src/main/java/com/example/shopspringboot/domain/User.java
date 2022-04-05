package com.example.shopspringboot.domain;

import com.example.shopspringboot.util.UserUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "receive_newsletters")
    private boolean receiveNewsletters;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "failed_signin_cnt")
    private int failedSignInCount;

    @Column(name = "user_ban_date")
    private Timestamp banDate;

    public User() {

    }

    public User(String first, String last, String email, String password, boolean newsletters) {
        firstName = first;
        lastName = last;
        this.email = email;
        this.password = password;
        receiveNewsletters = newsletters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isReceiveNewsletters() {
        return receiveNewsletters;
    }

    public void setReceiveNewsletters(boolean receiveNewsletters) {
        this.receiveNewsletters = receiveNewsletters;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getFailedSignInCount() {
        return failedSignInCount;
    }

    public void setFailedSignInCount(int failedSignInCount) {
        this.failedSignInCount = failedSignInCount;
    }

    public Timestamp getBanDate() {
        return banDate;
    }

    public void setBanDate(Timestamp banDate) {
        this.banDate = banDate;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" +
                lastName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +
                ", receiveNewsletters=" + receiveNewsletters + ", roleId=" + roleId +
                ", failedSignInCount=" + failedSignInCount + ", banDate=" + banDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && receiveNewsletters == user.receiveNewsletters &&
                roleId == user.roleId && failedSignInCount == user.failedSignInCount &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) && Objects.equals(banDate, user.banDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, receiveNewsletters, roleId,
                failedSignInCount, banDate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(UserRoles.getRole(roleId)));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserUtil.isBanned(this);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}