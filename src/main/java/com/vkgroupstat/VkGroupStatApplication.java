package com.vkgroupstat;

import com.vkgroupstat.model.Role;
import com.vkgroupstat.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VkGroupStatApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkGroupStatApplication.class, args);
	}
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {

		return args -> {
			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}
		};
	}
}
