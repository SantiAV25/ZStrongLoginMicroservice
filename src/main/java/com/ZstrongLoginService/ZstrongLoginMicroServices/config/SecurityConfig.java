package com.ZstrongLoginService.ZstrongLoginMicroServices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ZstrongLoginService.ZstrongLoginMicroServices.config.filter.JwtTokenValidator;
import com.ZstrongLoginService.ZstrongLoginMicroServices.service.UserDatailsServiceImpl;
import com.ZstrongLoginService.ZstrongLoginMicroServices.util.jwtUtils.JwtUtils;

import org.springframework.security.authentication.AuthenticationProvider;

/*
 * This is The Security Configuration Class That Will Handle The Security Configuration of The Application
 * @author Santiago Agredo Vallejo
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {


    @Autowired
    private JwtUtils jwtUtils;

    /**
     * This Method Will Create The Security Filter Chain to handle de security In the Application
     * @param httpSecurity 
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(http -> {
            http.requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll();
            http.requestMatchers(HttpMethod.GET,"/method/**").authenticated();
            http.anyRequest().denyAll();
        })
        .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
        .build();
    }

    /*
     * This Method Will Create The Authentication Manager Bean
     * @param authenticationConfiguration
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    
    }

    /*
     * This Method Will Create The Authentication Provider that is used when the user login in the application
     * @param userDetailsService 
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDatailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    /*
     * This Method Will Create The Password Encoder Bean
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
