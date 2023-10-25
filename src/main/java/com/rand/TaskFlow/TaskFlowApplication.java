package com.rand.TaskFlow;

import com.rand.TaskFlow.entity.Role;
import com.rand.TaskFlow.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaskFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo) {
		return args -> {
			if(roleRepo.findByAuthority("ROLE_MANAGER").isPresent()) return;
			roleRepo.save(new Role(null, "ROLE_MANAGER"));
			roleRepo.save(new Role(null, "ROLE_LEADER"));
			roleRepo.save(new Role(null, "ROLE_TEAM_MEMBER"));
		};
	}
}
