package com.ilungi.gestora.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.ilungi.gestora.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	@GetMapping
	public ResponseEntity<User> finaAll(){
		User l = new User(1L, "Luciano", "la@gmail.com", "99999", "12345");
		return ResponseEntity.ok().body(l);
	}

}