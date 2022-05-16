package com.example.demo.Security;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception{
        String role = "ROLE_USER";
        CustomFilter customFilter = new CustomFilter();
        customFilter.setAuthenticationManager(authenticationManager());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(GET,"/items").permitAll();
        http.authorizeRequests().antMatchers(POST,"/sign_up").permitAll();
        http.authorizeRequests().antMatchers(POST,"/login").permitAll();
        http.authorizeRequests().antMatchers(GET, "/customers/**").hasAnyAuthority(role);
        http.authorizeRequests().antMatchers(GET, "/items/**").hasAnyAuthority(role);
        http.authorizeRequests().antMatchers(POST, "/items/**").hasAnyAuthority(role);
        http.authorizeRequests().antMatchers(GET, "/orders/**").hasAnyAuthority(role);
        http.authorizeRequests().antMatchers(POST, "/orders/**").hasAnyAuthority(role);
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
