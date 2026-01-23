package com.ilungi.gestora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ilungi.gestora.entities.	Task;
import com.ilungi.gestora.entities.User;
import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository <Task, Long>{
	 @Query("SELECT t FROM Task t WHERE t.responsible = ?1")
	    List<Task> findByResponsible(User user);
}
