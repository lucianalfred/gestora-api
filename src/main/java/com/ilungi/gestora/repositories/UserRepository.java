package com.ilungi.gestora.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
	

   
    List<User> findByRole(Role role);
    
    List<User> findByNameContainingIgnoreCase(String name);
    
    boolean existsByEmail(String email);
    
    // Buscar por múltiplos roles
    @Query("SELECT u FROM User u WHERE u.role IN :roles")
    List<User> findByRoles(@Param("roles") List<Role> roles);
    
    // Contar por role
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") Role role);
    
    // Buscar usuários sem tasks atribuídas
    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT DISTINCT t.responsible.id FROM Task t)")
    List<User> findUsersWithoutTasks();
    
    // Buscar usuários com mais tasks
    @Query("SELECT u, COUNT(t) as taskCount FROM User u LEFT JOIN u.tasks t GROUP BY u ORDER BY taskCount DESC")
    List<Object[]> findUsersWithTaskCount();
}
