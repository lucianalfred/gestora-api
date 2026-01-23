package com.ilungi.gestora.servicies;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ilungi.gestora.repositories.TaskRepository;
import com.ilungi.gestora.repositories.UserRepository;
import com.ilungi.gestora.GestoraApplication;
import com.ilungi.gestora.config.SecurityUtil;
import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.TaskStatus;
import com.ilungi.gestora.entities.User;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SecurityUtil securityUtil;
    
    // ✅ADMIN vê todas, USER vê apenas suas tasks
    public List<Task> findAll() {
        User currentUser = securityUtil.getCurrentUser();
        
        if (currentUser.getRole() == Role.ADMIN) {
            return taskRepository.findAll();
        } else {
            return taskRepository.findByResponsible(currentUser);
        }
    }
    
    // ✅ ADMIN pode ver qualquer task, USER apenas se for responsável
    public Task findById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        
        User currentUser = securityUtil.getCurrentUser();
        
        // ADMIN pode ver qualquer task
        if (currentUser.getRole() == Role.ADMIN) {
            return task;
        }
        
        // USER só pode ver se for responsável
        if (!task.getResponsible().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Você não tem permissão para ver esta task");
        }
        
        return task;
    }
    
    // ✅ ADMIN pode criar task para qualquer usuário, USER só para si mesmo
    public Task createTask(Task task) {
        User currentUser = securityUtil.getCurrentUser();
        
        // Se USER e tentando atribuir para outro, bloqueia
        if (currentUser.getRole() == Role.USER) {
            if (task.getResponsible() == null || 
                !task.getResponsible().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Usuários comuns só podem criar tasks para si mesmos");
            }
        }
        
        // Se responsável não foi definido, usa o usuário atual
        if (task.getResponsible() == null) {
            task.setResponsible(currentUser);
        }
        
        // Garante que o responsável existe
        User responsible = userRepository.findById(task.getResponsible().getId())
            .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
        task.setResponsible(responsible);
        
        task.setCreateAt(new Date());
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        
        return taskRepository.save(task);
    }
    
    // ✅ ADMIN pode atualizar qualquer task, USER apenas suas tasks
    public Task updateTask(Long id, Task taskUpdates) {
        Task task = findById(id); // Já valida permissão de visualização
        
        User currentUser = securityUtil.getCurrentUser();
        
        // USER só pode atualizar certos campos
        if (currentUser.getRole() == Role.USER) {
            // USER só pode atualizar status e descrição/título
            if (taskUpdates.getStatus() != null) {
                task.setStatus(taskUpdates.getStatus());
            }
            if (taskUpdates.getTitle() != null) {
                task.setTitle(taskUpdates.getTitle());
            }
            if (taskUpdates.getDescription() != null) {
                task.setDescription(taskUpdates.getDescription());
            }
            // USER não pode mudar responsável nem data
        } else {
            // ADMIN pode atualizar tudo
            if (taskUpdates.getTitle() != null) {
                task.setTitle(taskUpdates.getTitle());
            }
            if (taskUpdates.getDescription() != null) {
                task.setDescription(taskUpdates.getDescription());
            }
            if (taskUpdates.getEndDate() != null) {
                task.setEndDate(taskUpdates.getEndDate());
            }
            if (taskUpdates.getStatus() != null) {
                task.setStatus(taskUpdates.getStatus());
            }
            if (taskUpdates.getResponsible() != null) {
                User responsible = userRepository.findById(taskUpdates.getResponsible().getId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
                task.setResponsible(responsible);
            }
        }
        
        return taskRepository.save(task);
    }
    
    // ADMIN pode deletar qualquer task, USER apenas suas tasks
    public void deleteTask(Long id) {
        Task task = findById(id); // Já valida permissão
        
        User currentUser = securityUtil.getCurrentUser();
        
        // USER só pode deletar se for responsável
        if (currentUser.getRole() == Role.USER && 
            !task.getResponsible().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar esta task");
        }
        
        taskRepository.delete(task);
    }
    
    //Métodos específicos para USER
    public Task updateMyTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        
        User currentUser = securityUtil.getCurrentUser();
        
        // Verifica se é o responsável
        if (!task.getResponsible().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Você só pode atualizar suas próprias tasks");
        }
        
        task.setStatus(status);
        return taskRepository.save(task);
    }
    
    public List<Task> findMyTasks() {
        User currentUser = securityUtil.getCurrentUser();
        return taskRepository.findByResponsible(currentUser);
    }
    
    //Métodos apenas para ADMIN
    @PreAuthorize("hasRole('ADMIN')")  // Anotação de segurança no método
    public List<Task> findAllTasksAdmin() {
        return taskRepository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    public Task assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        task.setResponsible(user);
        return taskRepository.save(task);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> findTasksByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return taskRepository.findByResponsible(user);
    }
}
