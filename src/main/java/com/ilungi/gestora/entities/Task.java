package com.ilungi.gestora.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_tasks")
public class Task implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private Date createAt;
	private Date endDate;
	private TaskStatus status;
	
	@ManyToOne
	@JoinColumn(name = "responsible_id")
	@JsonIgnoreProperties({"tasks", "password"})
	private User responsible ;
	
	
	
	public Task() {}


	public Task(Long id, String title, String description, Date createAt, Date endDate, User responsible) {
		super();
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.createAt = createAt;
		this.endDate = endDate;
		this.responsible = responsible;
		this.status = TaskStatus.PEDING;
	}

	
	
	

	public Task(Long id, String title, String description, Date createAt, Date endDate, TaskStatus status,
			User responsible) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createAt = createAt;
		this.endDate = endDate;
		this.status = status;
		this.responsible = responsible;
	}


	public TaskStatus getStatus() {
		return status;
	}


	public void setStatus(TaskStatus status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@JsonProperty("responsibleId")
	public Long getResponsibleId() {
	    return responsible != null ? responsible.getId() : null;
	}
	
	@JsonProperty("reponsibleName")
	public String getResponsibleName() {
		return responsible != null ? responsible.getName() : null;
	}
	
	
	public User getResponsible() {
		return responsible;
	}


	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(createAt, description, endDate, id, responsible, title);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(createAt, other.createAt) && Objects.equals(description, other.description)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(id, other.id)
				&& Objects.equals(responsible, other.responsible) && Objects.equals(title, other.title);
	}


	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", createAt=" + createAt
				+ ", endDate=" + endDate + ", responsible=" + responsible + "]";
	}
	
	
	
	
	
}
