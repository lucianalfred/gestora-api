package com.ilungi.gestora.resources;


import com.fasterxml.jackson.databind.JsonNode;
import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.TaskStatus;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.servicies.TaskService;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	/*@PostMapping("/create")
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
	}*/
	

	

	@PostMapping("/create")
	public ResponseEntity<Task> createTaskFromJson(@RequestBody Map<String, Object> json) {
	    try {
	        // Extrai os valores com segurança
	        String title = (String) json.getOrDefault("title", "Sem título");
	        String description = (String) json.getOrDefault("description", "");
	        
	        // Converte responsibleId para Long
	        Long responsibleId = null;
	        Object idObj = json.get("responsibleId");
	        if (idObj != null) {
	            if (idObj instanceof Integer) {
	                responsibleId = ((Integer) idObj).longValue();
	            } else if (idObj instanceof Long) {
	                responsibleId = (Long) idObj;
	            } else if (idObj instanceof String) {
	                try {
	                    responsibleId = Long.parseLong((String) idObj);
	                } catch (NumberFormatException e) {
	                    throw new RuntimeException("responsibleId deve ser um número válido");
	                }
	            }
	        }
	        
	        if (responsibleId == null) {
	            throw new RuntimeException("O campo 'responsibleId' é obrigatório");
	        }
	        
	        // Converte a data
	        Date endDate;
	        Object dateObj = json.get("endDate");
	        if (dateObj != null) {
	            try {
	                String dateStr = dateObj.toString();
	                endDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
	            } catch (Exception e) {
	                // Data padrão: 30 dias a partir de hoje
	                endDate = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
	            }
	        } else {
	            // Data padrão: 30 dias a partir de hoje
	            endDate = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
	        }
	        
	        // Cria a task
	        Task task = service.createTask(
	            title,
	            description,
	            new Date(), // Data atual para createAt
	            endDate,
	            null, // status (será PENDING por padrão no service)
	            responsibleId
	        );
	        
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(task.getId())
	                .toUri();
	        
	        return ResponseEntity.created(uri).body(task);
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao criar task: " + e.getMessage());
	    }
	
	  
	}

	//apaga a tarefa
   @DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id){
	
	   try {
		   service.deleteTask(id);
				
		   Map<String, String> response = new HashMap<>();
		   
		   response.put("message", "Tarefa apagada com sucesso!");
		   response.put("id", id.toString());
		   return ResponseEntity.ok(response);
	   }
	   catch (Exception e) {
		   Map<String, String> errorResponse = new HashMap<>();
		   errorResponse.put("error", "Tarefa não encontrada!");
	       errorResponse.put("id", id.toString());
		   return ResponseEntity.status(404).body(errorResponse);
	   }
	}
	
}
