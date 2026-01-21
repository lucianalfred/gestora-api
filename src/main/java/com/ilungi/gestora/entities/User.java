package com.ilungi.gestora.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="tb_users")
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
	private String password;
	
	
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	@OneToMany(mappedBy = "responsible")
	@JsonIgnore
	private List<Task> tasks = new ArrayList<>();
	
	
	public User() {}
	
	
	
	
	public User(Long id, String name, String email, String phone, String password, Role role) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.password = password;
		this.role = role;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	
}