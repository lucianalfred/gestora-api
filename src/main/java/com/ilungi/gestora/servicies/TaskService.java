package com.ilungi.gestora.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilungi.gestora.repositories.TaskRepository;
import com.ilungi.gestora.entities.Task;

@Service 
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	public List<Task> findAll(){
		return repository.findAll();
	}
	
	public Task findById(Long id) {
		Optional<Task> obj = repository.findById(id);
		
		return obj.get();
	}
}
