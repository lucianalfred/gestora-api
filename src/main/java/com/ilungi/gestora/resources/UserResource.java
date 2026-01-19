package com.ilungi.gestora.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ilungi.gestora.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	@GetMapping
	public ResponseEntity<User> findaAll(){
		//public User(Long id, String name, String email, String phone, String password)
		User lucian =  new User(1L, "Luciano", "lucianalfed60@gmail.com", "123333344", "pass123");
		return ResponseEntity.ok().body(lucian);
	}
}
