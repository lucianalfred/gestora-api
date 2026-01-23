package com.ilungi.gestora.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    public List<User> findAll() {
        return repository.findAll();
    }
    
    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }
    
    public User findByEmail(String email) {
       
        Optional<User> obj = repository.findByEmail(email);
        return obj.orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
    }
    

    public Optional<User> findByEmailOptional(String email) {
        return repository.findByEmail(email);
    }
    
    // Método para verificar se email existe
    public boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
}