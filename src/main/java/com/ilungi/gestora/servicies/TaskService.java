package com.ilungi.gestora.servicies;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilungi.gestora.repositories.TaskRepository;
import com.ilungi.gestora.repositories.UserRepository;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.TaskStatus;
import com.ilungi.gestora.entities.User;

@Service 
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Task> findAll(){
		return repository.findAll();
	}
	
	public Task findById(Long id) {
		Optional<Task> obj = repository.findById(id);
		
		return obj.get();
	}
	
	public Task createTask(String title, String description, Date createAt, Date endDate, TaskStatus status,
			Long responsibleId) {
		Optional<User> userOptional = userRepository.findById(responsibleId);
		
		if(!userOptional.isPresent())
				throw new RuntimeException("User not found with id" + responsibleId);
				User responsible = userOptional.get();
				Task task = new Task();
				task.setTitle(title);
				task.setDescription(description);
				task.setCreateAt(new Date());
				task.setEndDate(endDate);
				task.setResponsible(responsible);
				task.setStatus(TaskStatus.PEDING);
				return repository.save(task);
				
	}
	
	
	public Task createTask(Task task) {
	 
	    if (task.getResponsible() != null && task.getResponsible().getId() != null) {
	        User responsible = userRepository.findById(task.getResponsible().getId())
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + task.getResponsible().getId()));
	        task.setResponsible(responsible);
	    }
	    
	    return repository.save(task);
	}

}
