package com.ilungi.gestora.servicies;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ilungi.gestora.repositories.TaskRepository;
import com.ilungi.gestora.repositories.UserRepository;
import com.ilungi.gestora.GestoraApplication;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.TaskStatus;
import com.ilungi.gestora.entities.User;

@Service 
public class TaskService {

    private final GestoraApplication gestoraApplication;
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	

    TaskService(GestoraApplication gestoraApplication) {
        this.gestoraApplication = gestoraApplication;
    }
	
	public List<Task> findAll(){
		return repository.findAll();
	}
	
	public Task findById(Long id) {
		Optional<Task> obj = repository.findById(id);
		
		return obj.get();
	}
	

	/* Create Tasks
	 * 
	 */
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
	
	

	/* Update  Tasks
	 * 
	 */
	
	public Task updateFromJson(Long taskId, Map<String, Object> jsonData) {
      
        Task task = repository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task não encontrada: " + taskId));
        if (jsonData.containsKey("title")) {
            task.setTitle((String) jsonData.get("title"));
        }
        
        if (jsonData.containsKey("description")) {
            task.setDescription((String) jsonData.get("description"));
        }
        
        if (jsonData.containsKey("status")) {
            String statusStr = jsonData.get("status").toString().toUpperCase();
            try {
                task.setStatus(TaskStatus.valueOf(statusStr));
            } catch (Exception e) {
                // Ignora status inválido
            }
        }
        
        if (jsonData.containsKey("endDate")) {
            try {
                String dateStr = jsonData.get("endDate").toString();
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                task.setEndDate(date);
            } catch (Exception e) {
                // Ignora erro de data
            }
        }
        
        if (jsonData.containsKey("responsibleId")) {
            Long userId = Long.parseLong(jsonData.get("responsibleId").toString());
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
            task.setResponsible(user);
        }
        
        return repository.save(task);
    }
    
    public Task updateTask(Long id, Task taskData) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task não encontrada: " + id));
        
        if (taskData.getTitle() != null) {
            task.setTitle(taskData.getTitle());
        }
        
        if (taskData.getDescription() != null) {
            task.setDescription(taskData.getDescription());
        }
        
        if (taskData.getEndDate() != null) {
            task.setEndDate(taskData.getEndDate());
        }
        
        if (taskData.getStatus() != null) {
            task.setStatus(taskData.getStatus());
        }
        
        if (taskData.getResponsible() != null && taskData.getResponsible().getId() != null) {
            User user = userRepository.findById(taskData.getResponsible().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            task.setResponsible(user);
        }
        
        return repository.save(task);
    }
    
    
	/* Delete Tasks
	 * 
	 */
	public void deleteTask(Long id) {
		
		if (!repository.existsById(id)) {
			throw new RuntimeException("Task not found with id" + id);
		}
		repository.deleteById(id);
	}
	
	public Task deleteAndReturn(Long id) {
		Task task = findById(id);
		repository.delete(task);
		return task;
	}
	
	

}
