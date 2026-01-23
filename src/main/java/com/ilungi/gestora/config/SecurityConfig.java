package com.ilungi.gestora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // ✅ PERMITE TODOS OS RECURSOS DO H2-CONSOLE
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/h2-console/*").permitAll()
                .requestMatchers("/h2-console").permitAll()
                
                // Permite recursos estáticos do H2
                .requestMatchers("/*.css").permitAll()
                .requestMatchers("/*.js").permitAll()
                .requestMatchers("/*.gif").permitAll()
                .requestMatchers("/*.jpg").permitAll()
                .requestMatchers("/*.png").permitAll()
                .requestMatchers("/*.ico").permitAll()
                .requestMatchers("/*.jsp").permitAll()
                .requestMatchers("/*.do").permitAll()
                
                // Rotas públicas da sua API
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/tasks/admin/**").hasRole("ADMIN")
                .requestMatchers("/users/admin/**").hasRole("ADMIN")

                // Rotas que precisam de autenticação
                .requestMatchers("/tasks/**").authenticated()
                .requestMatchers("/users/**").authenticated()
                // Rotas que precisam de autenticação
                .requestMatchers("/tasks/**").authenticated()
                .requestMatchers("/users/**").authenticated()
                .requestMatchers(
                	    "/swagger-ui.html",
                	    "/swagger-ui/**",
                	    "/v3/api-docs/**",
                	    "/api-docs/**"
                	).permitAll()
                // Qualquer outra rota
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin()) // Permite iframes do mesmo origin
                .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


/**
*
*   

.anyRequest().authenticated()*/