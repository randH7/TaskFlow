package com.rand.TaskFlow.config;

import com.rand.TaskFlow.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/api/manager/**").hasAuthority("ROLE_MANAGER")

                        .requestMatchers("/api/employ/**").hasAuthority("ROLE_EMPLOY")

                        .requestMatchers(GET, "/api/projects").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOY")
                        .requestMatchers(GET, "/api/projects/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOY")
                        .requestMatchers(POST, "/api/projects/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers(PATCH, "/api/projects/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers(DELETE, "/api/projects/**").hasAuthority("ROLE_MANAGER")


                        .requestMatchers("/api/tasks/**").hasAuthority("ROLE_EMPLOY")

                        .anyRequest().authenticated())

                // Add the authentication filter to the http security object
                .authenticationProvider(authenticationProvider)

                // Add the custom authorization filter before the standard authentication filter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

}
