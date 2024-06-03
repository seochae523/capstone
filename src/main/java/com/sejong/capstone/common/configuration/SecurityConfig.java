package com.sejong.capstone.common.configuration;

import com.sejong.capstone.common.token.AuthTokenProvider;
import com.sejong.capstone.common.token.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthTokenProvider authTokenProvider;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll();
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/user/**").hasRole("USER")
         //       .and()
        //        .addFilterBefore(new JwtFilter(authTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
