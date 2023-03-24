package com.slavamashkov.onlineshoptest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/history").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/users/**").hasAnyRole("ADMIN")
                .requestMatchers("/product/add").hasAnyRole("USER","ADMIN")
                .requestMatchers("/product/update/*").hasAnyRole("ADMIN")
                .requestMatchers("/product/return/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/product/rate/*").hasAnyRole("USER", "ADMIN")
                //.requestMatchers("/product/delete").hasAnyRole("ADMIN")
                .requestMatchers("/product/info/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/product/buy/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/sale/**").hasAnyRole("ADMIN")
                .requestMatchers("/tag/**").hasAnyRole("ADMIN")
                .requestMatchers("/notification/send").hasAnyRole("ADMIN")
                .requestMatchers("/notification/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/organization/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        ).formLogin(form -> form
                .loginPage("/login")
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        ).exceptionHandling().accessDeniedPage("/403");

        return httpSecurity.build();
    }
}
