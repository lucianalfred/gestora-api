package com.ilungi.gestora.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	@GetMapping
	public ResponseEntity<User> finaAll(){
		//public User(Long id, String email, String phone, String name, String password, Role role) 
		User l = new User(1L, "la@gmail.com", "99999","Luciano", "123", Role.USER);
		return ResponseEntity.ok().body(l);
	}

}