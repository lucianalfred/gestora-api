package com.ilungi.gestora.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="tb_user")
public class User  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String phone;
	private String name;
	
	public User() {}
	
	public User(Long id, String name, String email, String phone, String password){
		
		super();
		this.id = id;
		this.name = name;
		this.email =  email;
		this.phone = phone;
		
	}

	@JsonProperty
	private Long getId() {
		return id;
	}
	
	@JsonProperty
	private void setId(Long id) {
		this.id = id;
	}

	@JsonProperty
	private String getEmail() {
		return email;
	}
	@JsonProperty
	private void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty
	private String getPhone() {
		return phone;
	}
	@JsonProperty
	private void setPhone(String phone) {
		this.phone = phone;
	}
	@JsonProperty
	private String getName() {
		return name;
	}
	@JsonProperty
	private void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", phone=" + phone + ", name=" + name + "]";
	}
	
	
	
	
	
}