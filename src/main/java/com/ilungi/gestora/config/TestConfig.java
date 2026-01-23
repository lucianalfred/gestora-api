package com.ilungi.gestora.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ilungi.gestora.GestoraApplication;
import com.ilungi.gestora.entities.Role;
import com.ilungi.gestora.entities.Task;
import com.ilungi.gestora.entities.User;
import com.ilungi.gestora.repositories.TaskRepository;
import com.ilungi.gestora.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final GestoraApplication gestoraApplication;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    TestConfig(GestoraApplication gestoraApplication) {
        this.gestoraApplication = gestoraApplication;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Criando 10 usuários
    	//User(Long id, String name, String email, String phone, String password, Role role)
        User u1 = new User(null, "Antonio Silva", "antonio@gmail.com", "199999001", "123456", Role.ADMIN); 
        User u2 = new User(null, "Leonor Flora", "leonorflora@gmail.com", "199999002", "123456", Role.USER); 
        User u3 = new User(null, "Maria Santos", "maria.santos@gmail.com", "199999003", "123456", Role.USER);
        User u4 = new User(null, "Carlos Oliveira", "carlos.oliveira@gmail.com", "199999004", "123456", Role.ADMIN);
        User u5 = new User(null, "Ana Pereira", "ana.pereira@gmail.com", "199999005", "123456", Role.USER);
        User u6 = new User(null, "João Costa", "joao.costa@gmail.com", "199999006", "123456", Role.USER);
        User u7 = new User(null, "Sofia Rodrigues", "sofia.rodrigues@gmail.com", "199999007", "123456", Role.USER);
        User u8 = new User(null, "Pedro Almeida", "pedro.almeida@gmail.com", "199999008", "123456", Role.ADMIN);
        User u9 = new User(null, "Laura Martins", "laura.martins@gmail.com", "199999009", "123456", Role.USER);
        User u10 = new User(null, "Ricardo Sousa", "ricardo.sousa@gmail.com", "199999010", "123456", Role.USER);
        
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8, u9, u10));
        
        // Criando datas para as tasks
        Date now = Date.from(Instant.now());
        Date tomorrow = Date.from(Instant.now().plusSeconds(86400)); // +1 dia
        Date nextWeek = Date.from(Instant.now().plusSeconds(604800)); // +7 dias
        Date yesterday = Date.from(Instant.now().minusSeconds(86400)); // -1 dia
        Date nextMonth = Date.from(Instant.now().plusSeconds(2592000)); // +30 dias
        
        // Criando 10 tasks
        Task t1 = new Task(null, "Implementar Login", "Criar sistema de autenticação JWT", now, tomorrow, u1);
        Task t2 = new Task(null, "Criar Dashboard", "Desenvolver dashboard administrativo", now, nextWeek, u2);
        Task t3 = new Task(null, "Testar API", "Realizar testes unitários e de integração", yesterday, now, u3);
        Task t4 = new Task(null, "Documentar Projeto", "Criar documentação técnica", now, nextWeek, u4);
        Task t5 = new Task(null, "Configurar CI/CD", "Configurar pipeline de deploy automático", now, nextMonth, u5);
        Task t6 = new Task(null, "Otimizar Banco", "Criar índices e otimizar queries", yesterday, tomorrow, u6);
        Task t7 = new Task(null, "Design Frontend", "Criar layout da interface do usuário", now, nextWeek, u7);
        Task t8 = new Task(null, "Configurar Docker", "Dockerizar a aplicação", now, tomorrow, u8);
        Task t9 = new Task(null, "Monitoramento", "Configurar logs e métricas", now, nextMonth, u9);
        Task t10 = new Task(null, "Backup Sistema", "Implementar sistema de backup automático", yesterday, now, u10);
        
        taskRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        
        System.out.println("Dados de teste carregados com sucesso!");
        System.out.println("Usuários criados: " + userRepository.count());
        System.out.println("Tasks criadas: " + taskRepository.count());
        
        
        
        
        
            // Criar usuário ADMIN
        User admin = new User();
        admin.setName("Administrador");
        admin.setEmail("admin@system.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setPhone("000000000");
        admin.setRole(com.ilungi.gestora.entities.Role.ADMIN);
            
        if (userRepository.findByEmail(admin.getEmail()).isEmpty()) {
              userRepository.save(admin);
             System.out.println("Usuário ADMIN criado: admin@system.com / admin123");
        }
            
       // Criar usuário comum
       User user = new User();
       user.setName("Usuário Comum");
       user.setEmail("user@test.com");
       user.setPassword(passwordEncoder.encode("123456"));
       user.setPhone("999999999");
       user.setRole(com.ilungi.gestora.entities.Role.USER);
            
       if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
          userRepository.save(user);
          System.out.println("✅ Usuário USER criado: user@test.com / 123456");
       }
        
    }
}