package com.example.jobportal;

import com.example.jobportal.Role.ERole;
import com.example.jobportal.model.Role;
import com.example.jobportal.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobportalApplication {

	public static void main(String[] args) {

		SpringApplication.run(JobportalApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.count() == 0) {
				roleRepository.save(new Role(ERole.ROLE_APPLICANT));
				roleRepository.save(new Role(ERole.ROLE_RECRUITER));
				roleRepository.save(new Role(ERole.ROLE_CANDIDATE));
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
				//System.out.println(">>> Roles inserted into DB.");
			}
		};
	}

}
