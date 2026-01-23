package com.ilungi.gestora.resources;

import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.servicies.TaskService;
import com.ilungi.gestora.servicies.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Todas as rotas aqui precisam de role ADMIN
public class AdminResource {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;
    
    // ========== ENDPOINTS PARA USUÁRIOS ==========
    
    // 1. Listar todos os usuários
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    
    // 2. Buscar usuário por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    // 3. Criar novo usuário (admin pode criar qualquer tipo)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }
    
    // 4. Atualizar qualquer usuário
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userData) {
        User updatedUser = userService.updateUser(id, userData);
        return ResponseEntity.ok(updatedUser);
    }
    
    // 5. Deletar usuário
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // 6. Mudar role de usuário
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<User> changeUserRole(@PathVariable Long id, @RequestParam String role) {
        com.ilungi.gestora.entities.Role userRole = com.ilungi.gestora.entities.Role.valueOf(role.toUpperCase());
        User updatedUser = userService.changeRole(id, userRole);
        return ResponseEntity.ok(updatedUser);
    }
    
    // 7. Buscar usuários por role
    @GetMapping("/users/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        com.ilungi.gestora.entities.Role userRole = com.ilungi.gestora.entities.Role.valueOf(role.toUpperCase());
        List<User> users = userService.findByRole(userRole);
        return ResponseEntity.ok(users);
    }
    
    // ========== ENDPOINTS PARA TASKS ==========
    
    // 8. Ver todas as tasks (sem filtro)
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        // Você precisa criar este método no TaskService
        // List<Task> tasks = taskService.findAllTasksAdmin();
        // Por enquanto, vamos usar o findAll normal
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }
    
    // 9. Ver tasks de um usuário específico (ESTE É O QUE VOCÊ TENTOU ACESSAR)
    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        // Você precisa criar este método no TaskService
        // Por enquanto, vamos filtrar manualmente
        List<Task> allTasks = taskService.findAll();
        List<Task> userTasks = allTasks.stream()
            .filter(task -> task.getResponsible() != null && 
                           task.getResponsible().getId().equals(userId))
            .toList();
        
        return ResponseEntity.ok(userTasks);
    }
    
    // 10. Atribuir task para usuário
    @PatchMapping("/tasks/{taskId}/assign/{userId}")
    public ResponseEntity<Task> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        // Você precisa implementar este método
        // Task task = taskService.assignTaskToUser(taskId, userId);
        // Por enquanto, retorna not implemented
        return ResponseEntity.status(501).body(null);
    }
    
    // 11. Estatísticas gerais
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<User> allUsers = userService.findAll();
        List<Task> allTasks = taskService.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", allUsers.size());
        stats.put("totalTasks", allTasks.size());
        stats.put("adminCount", allUsers.stream()
            .filter(u -> u.getRole() == com.ilungi.gestora.entities.Role.ADMIN)
            .count());
        stats.put("userCount", allUsers.stream()
            .filter(u -> u.getRole() == com.ilungi.gestora.entities.Role.USER)
            .count());
        
        return ResponseEntity.ok(stats);
    }
    
    // 12. Dashboard info
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Adicione mais informações conforme necessário
        dashboard.put("message", "Dashboard administrativo");
        dashboard.put("endpoints", List.of(
            "GET /admin/users",
            "GET /admin/users/{id}",
            "GET /admin/tasks/user/{userId}",
            "GET /admin/stats"
        ));
        
        return ResponseEntity.ok(dashboard);
    }
}