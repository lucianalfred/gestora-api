package com.ilungi.gestora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ilungi.gestora.entities.	Task;
@Repository
public interface TaskRepository  extends JpaRepository <Task, Long>{

}
