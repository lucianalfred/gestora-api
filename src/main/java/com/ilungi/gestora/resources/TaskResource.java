package com.ilungi.gestora.resources;


import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.servicies.TaskService;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(value = "/tasks")
public class TaskResource {
	@Autowired
	private TaskService service;
	
	@GetMapping
	public ResponseEntity<List<Task>> findAll(){
		List<Task> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Task> findById(@PathVariable Long id){
		Task obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Task> createTask(
			@RequestParam String title,
			@RequestParam String description,
			@RequestParam Date createAt, 
			@RequestParam Date endDate, 
			@RequestParam Long responsibleId) {
		
		Task task = service.createTask(title, description, createAt, endDate, null, responsibleId);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/tasks/{id}")
                .buildAndExpand(task.getId())
                .toUri();
		return ResponseEntity.created(uri).body(task);
	}
}
