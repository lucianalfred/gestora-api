package com.ilungi.gestora.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.repositories.UserRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Antonio Silva", "anotonio@gmail.com", "1999990", "1232", Role.ADMIN); 
		User u2 = new User(null, "Leonor Flora", "leonorflora@gmail.com", "99990", "1232", Role.USER); 
		
		userRepository.saveAll(Arrays.asList(u1, u2));
		
	}
}
