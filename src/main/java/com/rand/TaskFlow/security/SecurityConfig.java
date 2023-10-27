package com.rand.TaskFlow.security;

import com.rand.TaskFlow.filters.CustomAuthenticationFilter;
import com.rand.TaskFlow.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManagerBuilder authManagerBuilder;


    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CustomAuthenticationFilter instance created
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        // set the URL that the filter should process
        customAuthenticationFilter.setFilterProcessesUrl("/taskflow/sign-in");



        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS));
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/taskflow/sign-in").permitAll()
                .requestMatchers("/taskflow/sign-up").permitAll()
                .requestMatchers(GET, "/taskflow/dashboard").hasAnyAuthority("ROLE_MANAGER", "ROLE_TEAM_MEMBER")
                .requestMatchers(GET, "/taskflow/projects").hasAnyAuthority("ROLE_MANAGER", "ROLE_TEAM_MEMBER")
                .requestMatchers(POST, "/taskflow/projects/create-project").hasAnyAuthority("ROLE_MANAGER")
                .requestMatchers(PATCH, "/taskflow/projects/edit-project/{projectId}").hasAnyAuthority("ROLE_MANAGER")
                .requestMatchers(DELETE, "/taskflow/projects/delete-project/{projectId}").hasAnyAuthority("ROLE_MANAGER")
                .requestMatchers(POST, "/taskflow/projects/{projectId}/add-task").hasAnyAuthority("ROLE_TEAM_MEMBER")
                .requestMatchers(GET, "/taskflow/my-tasks").hasAnyAuthority("ROLE_TEAM_MEMBER")
                .requestMatchers(PATCH, "/taskflow/projects/{projectId}/edit-tasks/{taskId}").hasAnyAuthority("ROLE_TEAM_MEMBER")
                .requestMatchers(DELETE, "/taskflow/projects/{projectId}/delete-tasks/{taskId}").hasAnyAuthority("ROLE_TEAM_MEMBER")
                .anyRequest().authenticated());

        http.formLogin(formLogin -> formLogin.loginPage("/taskflow/sign-in").permitAll());


        // add the custom authentication filter to the http security object
        http.addFilter(customAuthenticationFilter);
        // Add the custom authorization filter before the standard authentication filter.
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

}
