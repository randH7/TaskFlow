package com.rand.TaskFlow.config;

import com.rand.TaskFlow.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers(PATCH, "/api/manager/invite-employ").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers(PATCH, "/api/manager/remove-employ").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers(PATCH, "/api/manager/get-employees").hasAnyAuthority("ROLE_MANAGER")

                        .requestMatchers(GET, "/api/projects").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOY")
                        .requestMatchers(POST, "/api/projects/create-project").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers(PATCH, "/api/projects/edit-project/{projectId}").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers(DELETE, "/api/projects/delete-project/{projectId}").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers(POST, "/api/projects/{projectId}/add-task").hasAnyAuthority("ROLE_EMPLOY")
                        .requestMatchers(GET, "/api/my-tasks").hasAnyAuthority("ROLE_EMPLOY")
                        .requestMatchers(PATCH, "/api/projects/{projectId}/edit-tasks/{taskId}").hasAnyAuthority("ROLE_EMPLOY")
                        .requestMatchers(DELETE, "/api/projects/{projectId}/delete-tasks/{taskId}").hasAnyAuthority("ROLE_EMPLOY")
                        .anyRequest().authenticated())

                // Add the custom authentication filter to the http security object
                .authenticationProvider(authenticationProvider)

                // Add the custom authorization filter before the standard authentication filter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

}
