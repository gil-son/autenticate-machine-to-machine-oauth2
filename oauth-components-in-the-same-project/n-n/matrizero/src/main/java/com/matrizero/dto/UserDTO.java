package com.matrizero.dto;

import com.matrizero.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	
	@NotBlank(message = "Required field")
	private String firstName;
	
	@NotBlank(message = "Required field")
	private String lastName;
	
	@Email(message = "Type a valid email")
	@NotBlank(message = "Required field")
	private String email;
	// private String password; // Don't let password pass on DTO
	
	public UserDTO(){}

	Set<RoleDTO> roles = new HashSet<>();
	
	
	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserDTO(User entity) { // the FetchType.EAGER in the class User get the Role. The RoleDTO can be used with UserDTO
		id = entity.getId();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

}
