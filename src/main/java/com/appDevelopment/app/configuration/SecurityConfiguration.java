package com.appDevelopment.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()  // No authentication for GET
                        .requestMatchers(HttpMethod.POST, "/artist/**", "/artwork/**").hasAnyRole("MANAGER", "BUYER")
                        .requestMatchers(HttpMethod.PUT, "artwork/changePrice/**").hasAnyRole("MANAGER","BUYER")
                        .requestMatchers(HttpMethod.PUT, "/artwork/**", "artist/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/artist/**", "/artwork/**").hasAnyRole("MANAGER", "BUYER")
                        .requestMatchers("/employee/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Use default login form
                .httpBasic(withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory users for demonstration purposes; typically, you would load these from a database
        UserDetails manager = User.withDefaultPasswordEncoder()
                .username("manager")
                .password("password")
                .roles("MANAGER")
                .build();

        UserDetails buyer = User.withDefaultPasswordEncoder()
                .username("buyer")
                .password("password")
                .roles("BUYER")
                .build();

        UserDetails staff = User.withDefaultPasswordEncoder()
                .username("staff")
                .password("password")
                .roles("STAFF")
                .build();

        return new InMemoryUserDetailsManager(manager, buyer, staff);
    }
}
