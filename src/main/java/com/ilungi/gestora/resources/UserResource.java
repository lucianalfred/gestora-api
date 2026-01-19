package com.ilungi.gestora.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.servicies.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> finaAll(){
		
		List<User> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}

}