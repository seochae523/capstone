package com.sejong.capstone.user.domain;


import com.sejong.capstone.common.emun.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.persistence.*;


import java.util.ArrayList;
import java.util.Collection;


@Entity(name="user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class User implements UserDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "varchar(50)")
    private String email;


    @Column(columnDefinition = "varchar(10)")
    private String name;


    @Column(columnDefinition = "varchar(255)")
    private String password;


    @Column(columnDefinition = "varchar(255)")
    private String refreshToken;


    @Column(columnDefinition = "varchar(50)")
    private String role;


    public void setRole(Role role){
        if(this.role == null) {
            this.role = role.getValue();
        }
        else{
            this.role += ",";
            this.role += role.getValue();
        }
    }
    public void setRefreshToken(String refreshToken){
        this.refreshToken =refreshToken;
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken=refreshToken;
    }
    public User hashPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public Boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(rawPassword, this.password);
    }
    public void updatePassword(String password){
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : this.role.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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
