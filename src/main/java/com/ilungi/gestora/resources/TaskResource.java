package com.ilungi.gestora.resources;


import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.User;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/tasks")
public class TaskResource {
	@GetMapping
	public ResponseEntity<Task> findAll(){
		//public Task(Long id, String title, String description, Date createAt, Date endDate, User responsible)
		User l = new User(1L, "la@gmail.com", "99999","Luciano", "123", Role.USER);
		Task t =  new Task(1L, "Beber cafe", "Ir a copa beber cafe", new Date(2026, 1, 20), new Date(2026, 2, 20), l);
		return ResponseEntity.ok().body(t);
	}
	
	
}
